package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Province {
    private int iProvinceID = 0;
    protected Save_Provinces_GameData saveProvinceData;
    private Province_DrawArmy drawArmy;
    private String sProvinceName = null;
    private int iContinentID;
    private int iRegionID;
    private int iTerrainTypeID;
    private boolean seaProvince = false;
    private List lCities;
    private boolean drawCities = false;
    private int iCitiesSize = 0;
    private List lMountains;
    private List lWonders;
    private int iWondersSize = 0;
    protected float fPopulationGrowthRate;
    private int iPotential = 0;
    private int iDangerLevel = 0;
    private int iDangerLevel_WithArmy = 0;
    private int iRecruitableArmyPoints = 0;
    private byte iArmyWasRecruited = 0;
    private byte iNumOfNeighboringNeutralProvinces = 0;
    private boolean bordersWithEnemy = false;
    private float fProvinceStability = 1.0F;
    private List lNeighboringProvinces = new ArrayList();
    private List lNeighboringSeaProvinces = new ArrayList();
    private int iBasin = -1;
    private int iNeighboringProvincesSize;
    private int iNeighboringSeaProvincesSize;
    private List lProvinceBordersLandByLand = null;
    private List lProvinceBordersLandBySea = null;
    private List lProvinceBordersSeaBySea = null;
    private int iProvinceBordersLandByLandSize = 0;
    private int iProvinceBordersLandBySeaSize = 0;
    private int iProvinceBordersSeaBySeaSize = 0;
    private int iCivRegionID = -1;
    private Image provinceBG = null;
    private List lPointsX = new ArrayList();
    private List lPointsY = new ArrayList();
    private int iPointsSize;
    private int iMinX;
    private int iMinY;
    private int iMaxX;
    private int iMaxY;
    private int iCenterX;
    private int iCenterY;
    private int iShiftX;
    private int iShiftY;
    private List lProvince_ArmyBoxes = null;
    private boolean belowZeroPosX = false;
    private int iTranslateProvincePosX = 0;
    private boolean drawProvince = false;
    private Province_Port_Center provincePort = new Province_Port_Center(0, 0);
    private int iFromCivID = -1;
    private long lColorTime = 0L;
    private boolean updateColorTime = false;
    protected boolean viewBool = false;
    protected boolean was = false;
    protected float iIncome_Taxation = 1.0F;
    protected float iIncome_Production = 1.0F;
    protected float iAdministrationCost = 0.0F;
    protected static final float STABILITY_LARGEST_GROUP = 0.215F;
    protected static final float STABILITY_PERC_OF_TOTAL = 1.275F;
    protected static final float STABILITY_HAPPINESS = 0.3F;
    protected static final float STABILITY_HAPPINESS_MIN = 0.5F;
    protected static final float STABILITY_HAPPINESS_MIN_LOWER_STABILITY = -0.55F;
    protected static final float STABILITY_REV_RISK = 0.2F;
    protected static final float STABILITY_ARMY = 0.65F;
    protected static final float STABILITY_OCCUPIED = 0.45F;
    protected static final float STABILITY_DISEASE = 0.2F;
    protected static final float STABILITY_CORE = 0.05F;
    protected static final int MAX_DEFENSIVE_POSITION = 50;

    protected final int getBalance_LastTurn() {
        return (int)(this.iIncome_Taxation + this.iIncome_Production - this.iAdministrationCost);
    }

    protected Province(int iProvinceID, Province_GameData2 nProvince_GameData) {
        this.iProvinceID = iProvinceID;
        this.saveProvinceData = new Save_Provinces_GameData();
        this.saveProvinceData.oPopulation = new Province_Population();

        int i;
        for(i = 0; i < nProvince_GameData.getNeighboringProvinces().size(); ++i) {
            this.lNeighboringProvinces.add((Short)nProvince_GameData.getNeighboringProvinces().get(i));
        }

        this.iNeighboringProvincesSize = this.lNeighboringProvinces.size();

        for(i = 0; i < nProvince_GameData.getNeighboringSeaProvinces().size(); ++i) {
            this.lNeighboringSeaProvinces.add((Short)nProvince_GameData.getNeighboringSeaProvinces().get(i));
        }

        this.iNeighboringSeaProvincesSize = this.lNeighboringSeaProvinces.size();
        this.iMinX = this.iMaxX = (Short)nProvince_GameData.getPointsX().get(0);
        this.iMinY = this.iMaxY = (Short)nProvince_GameData.getPointsY().get(0);
        i = 0;

        for(int iSize = nProvince_GameData.getPointsX().size(); i < iSize; ++i) {
            this.lPointsX.add((Short)nProvince_GameData.getPointsX().get(i));
            this.lPointsY.add((Short)nProvince_GameData.getPointsY().get(i));
            if (this.iMinX > (Short)nProvince_GameData.getPointsX().get(i)) {
                this.iMinX = (Short)nProvince_GameData.getPointsX().get(i);
            }

            if (this.iMaxX < (Short)nProvince_GameData.getPointsX().get(i)) {
                this.iMaxX = (Short)nProvince_GameData.getPointsX().get(i);
            }

            if (this.iMinY > (Short)nProvince_GameData.getPointsY().get(i)) {
                this.iMinY = (Short)nProvince_GameData.getPointsY().get(i);
            }

            if (this.iMaxY < (Short)nProvince_GameData.getPointsY().get(i)) {
                this.iMaxY = (Short)nProvince_GameData.getPointsY().get(i);
            }
        }

        this.lPointsX.add((Short)nProvince_GameData.getPointsX().get(nProvince_GameData.getPointsX().size() - 1));
        this.lPointsY.add((Short)nProvince_GameData.getPointsY().get(nProvince_GameData.getPointsY().size() - 1));
        this.iPointsSize = this.lPointsX.size();
        this.iCenterX = (short)((this.iMinX + this.iMaxX) / 2);
        this.iCenterY = (short)((this.iMinY + this.iMaxY) / 2);
        this.belowZeroPosX = this.iMinX < 0;
        if (Game.MAX_BELOW_ZERO_POINT_X > this.iMinX) {
            Game.MAX_BELOW_ZERO_POINT_X = this.iMinX;
        }

        this.setLevelOfPort(nProvince_GameData.getLevelOfPort());
        if (nProvince_GameData.getLevelOfPort() < -1) {
            this.seaProvince = true;
        }

        if (nProvince_GameData.getProvinceBorder() != null) {
            this.lProvinceBordersLandByLand = new ArrayList();
            this.lProvinceBordersLandBySea = new ArrayList();
            this.lProvinceBordersSeaBySea = new ArrayList();

            for(i = 0; i < nProvince_GameData.getProvinceBorder().size(); ++i) {
                if (this.saveProvinceData.iPort < -1) {
                    this.lProvinceBordersSeaBySea.add(new Province_Border(((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getWithProvinceID(), ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getPointsX(), ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getPointsY()));
                } else {
                    boolean bContinue = false;

                    int j;
                    for(j = 0; j < this.iNeighboringProvincesSize; ++j) {
                        if ((Short)this.lNeighboringProvinces.get(j) == ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getWithProvinceID()) {
                            this.lProvinceBordersLandByLand.add(new Province_Border(((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getWithProvinceID(), ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getPointsX(), ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getPointsY()));
                            bContinue = true;
                            break;
                        }
                    }

                    if (!bContinue) {
                        for(j = 0; j < this.iNeighboringSeaProvincesSize; ++j) {
                            if ((Short)this.lNeighboringSeaProvinces.get(j) == ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getWithProvinceID()) {
                                this.lProvinceBordersLandBySea.add(new Province_Border(((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getWithProvinceID(), ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getPointsX(), ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getPointsY()));
                                bContinue = true;
                                break;
                            }
                        }

                        if (!bContinue) {
                            Gdx.app.log("Province CONSTRUCTOR", "PROVINCE BORDER CONNECTION ERROR: " + iProvinceID + " - " + ((Province_Border_GameData)nProvince_GameData.getProvinceBorder().get(i)).getWithProvinceID());
                        }
                    }
                }
            }

            if (this.lProvinceBordersLandByLand.size() == 0) {
                this.lProvinceBordersLandByLand = null;
            } else {
                this.iProvinceBordersLandByLandSize = this.lProvinceBordersLandByLand.size();
            }

            if (this.lProvinceBordersLandBySea.size() == 0) {
                this.lProvinceBordersLandBySea = null;
            } else {
                this.iProvinceBordersLandBySeaSize = this.lProvinceBordersLandBySea.size();
            }

            if (this.lProvinceBordersSeaBySea.size() == 0) {
                this.lProvinceBordersSeaBySea = null;
            } else {
                this.iProvinceBordersSeaBySeaSize = this.lProvinceBordersSeaBySea.size();
            }
        }

        this.loadProvinceInfo(nProvince_GameData.provinceInfo);
        this.lCities = new ArrayList();
        this.lMountains = new ArrayList();
        this.lWonders = new ArrayList();
        this.provincePort.iShiftX = (int)((float)nProvince_GameData.iPort_ShiftX * (float)CFG.map.getMapBG().getMapScale() / (float)CFG.map.getMapDefaultScale(CFG.map.getActiveMapID()));
        this.provincePort.iShiftY = (int)((float)nProvince_GameData.iPort_ShiftY * (float)CFG.map.getMapBG().getMapScale() / (float)CFG.map.getMapDefaultScale(CFG.map.getActiveMapID()));
        this.addCiv(0, 0);
    }

    protected final void loadProvinceInfo() {
        FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + this.iProvinceID);

        try {
            this.loadProvinceInfo((Province_Info_GameData3)CFG.deserialize(file.readBytes()));
            return;
        } catch (ClassNotFoundException var3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var3);
            }
        } catch (IOException var4) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var4);
            }
        } catch (GdxRuntimeException var5) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var5);
            }
        }

        this.iContinentID = 1;
        this.fPopulationGrowthRate = 1.0F;
        if (this.getLevelOfPort() >= -1) {
            this.iTerrainTypeID = 1;
        } else {
            this.iTerrainTypeID = 0;
        }

    }

    protected final void loadProvinceInfo(Province_Info_GameData3 tempGameData) {
        try {
            this.fPopulationGrowthRate = tempGameData.fGrowthRate;
            this.iContinentID = tempGameData.iContinentID;
            if (this.iContinentID >= CFG.map.getMapContinents().getContinentsSize()) {
                this.iContinentID = 1;
            }

            this.iRegionID = tempGameData.iRegionID;
            this.iShiftX = tempGameData.iShiftX * CFG.map.getMapScale(CFG.map.getActiveMapID()) / CFG.map.getMapDefaultScale(CFG.map.getActiveMapID());
            this.iShiftY = tempGameData.iShiftY * CFG.map.getMapScale(CFG.map.getActiveMapID()) / CFG.map.getMapDefaultScale(CFG.map.getActiveMapID());
            if (this.getSeaProvince()) {
                this.iTerrainTypeID = 0;

                try {
                    FileHandle fileBoxes = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "army_boxes/" + this.iProvinceID);
                    String sFileBoxes = fileBoxes.readString();
                    String[] tempData = sFileBoxes.split(";");
                    this.lProvince_ArmyBoxes = new ArrayList();

                    for(int i = tempData.length - 4; i >= 0; i -= 4) {
                        this.lProvince_ArmyBoxes.add(new Province_ArmyBox(Integer.parseInt(tempData[i]) * CFG.map.getMapBG().getMapScale(), Integer.parseInt(tempData[i + 1]) * CFG.map.getMapBG().getMapScale(), Integer.parseInt(tempData[i + 2]) * CFG.map.getMapBG().getMapScale(), Integer.parseInt(tempData[i + 3]) * CFG.map.getMapBG().getMapScale()));
                    }
                } catch (GdxRuntimeException var6) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(var6);
                    }
                }
            } else {
                this.iTerrainTypeID = CFG.terrainTypesManager.getTerrainTypeID(tempGameData.sTerrainTAG);
            }

        } catch (GdxRuntimeException var7) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var7);
            }

            this.iContinentID = 1;
            this.fPopulationGrowthRate = 1.0F;
            if (this.getLevelOfPort() >= -1) {
                this.iTerrainTypeID = 1;
            } else {
                this.iTerrainTypeID = 0;
            }

        }
    }

    protected final void checkLandBySeaProvinceBorders() {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getSeaProvince()) {
                if (this.lProvinceBordersLandBySea == null) {
                    this.lProvinceBordersLandBySea = new ArrayList();
                }

                this.lProvinceBordersLandBySea.add((Province_Border)this.lProvinceBordersLandByLand.get(i));
                this.lProvinceBordersLandByLand.remove(i);
                --i;
                this.iProvinceBordersLandBySeaSize = this.lProvinceBordersLandBySea.size();
                this.iProvinceBordersLandByLandSize = this.lProvinceBordersLandByLand.size();
            }
        }

    }

    protected final void checkSeaBySeaProvinceBorders() {
        for(int i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            if (!CFG.game.getProvince(((Province_Border)this.lProvinceBordersSeaBySea.get(i)).getWithProvinceID()).getSeaProvince()) {
                if (this.lProvinceBordersLandBySea == null) {
                    this.lProvinceBordersLandBySea = new ArrayList();
                }

                this.lProvinceBordersLandBySea.add((Province_Border)this.lProvinceBordersSeaBySea.get(i));
                this.lProvinceBordersSeaBySea.remove(i);
                --i;
                this.iProvinceBordersLandBySeaSize = this.lProvinceBordersLandBySea.size();
                this.iProvinceBordersSeaBySeaSize = this.lProvinceBordersSeaBySea.size();
            }
        }

    }

    protected final void loadProvinceBG() {
        try {
            Pixmap pixmap = PixmapIO.readCIM(Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "scales/" + "provinces/" + (this.iContinentID == CFG.map.getMapContinents().getOceanContinentID() ? 1 : CFG.map.getMapBG().getMapScale()) + "/" + this.iProvinceID));
            this.provinceBG = new Image(new Texture(pixmap), Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            pixmap.dispose();

            //optimized change//
            //this.provinceBG = new Image(new Texture(PixmapIO.readCIM(Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "scales/" + "provinces/" + (this.iContinentID == CFG.map.getMapContinents().getOceanContinentID() ? 1 : CFG.map.getMapBG().getMapScale()) + "/" + this.iProvinceID))), Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
        } catch (GdxRuntimeException var2) {
            this.buildProvinceBG(false);
            this.loadProvinceBG();
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }
        }

    }

    protected final Image getProvinceBG() {
        return this.provinceBG;
    }

    protected final void disposeProvinceBG() {
        this.provinceBG.getTexture().dispose();
    }

    protected final void setBG(Pixmap pixmap) {
        this.provinceBG.getTexture().dispose();
        this.provinceBG = null;
        this.provinceBG = new Image(new Texture(pixmap), Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
    }

    protected final void buildProvinceBG(boolean overwriteExistingFiles) {
        int tempMapScaleBefore = CFG.map.getMapScale(CFG.map.getActiveMapID());
        FileHandle tempFileT = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "scales/" + "provinces/" + "Age_of_Civilizations");
        String tempT = tempFileT.readString();
        String[] tagsSPLITED = tempT.split(";");
        List tempL = new ArrayList();

        for(int i = 0; i < tagsSPLITED.length; ++i) {
            tempL.add(tagsSPLITED[i]);
        }

        boolean addStandardScale = true;
        boolean addScale1 = true;

        int i;
        for(i = 0; i < tempL.size(); ++i) {
            if (Integer.parseInt((String)tempL.get(i)) == CFG.map.getMapScale(CFG.map.getActiveMapID())) {
                addStandardScale = false;
                break;
            }
        }

        for(i = 0; i < tempL.size(); ++i) {
            if (Integer.parseInt((String)tempL.get(i)) == 1) {
                addScale1 = false;
                break;
            }
        }

        if (addStandardScale) {
            tempL.add("" + CFG.map.getMapScale(CFG.map.getActiveMapID()));
        }

        if (addScale1 && CFG.map.getMapScale(CFG.map.getActiveMapID()) != 1) {
            tempL.add("1");
        }

        for(i = 0; i < tempL.size(); ++i) {
            CFG.map.setMapScale(CFG.map.getActiveMapID(), Integer.parseInt((String)tempL.get(i)));
            if (overwriteExistingFiles || !Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "scales/" + "provinces/" + CFG.map.getMapBG().getMapScale() + "/" + this.iProvinceID).exists()) {
                Pixmap pixmap = new Pixmap(this.iMaxX * CFG.map.getMapBG().getMapScale() - this.iMinX * CFG.map.getMapBG().getMapScale(), this.iMaxY * CFG.map.getMapBG().getMapScale() - this.iMinY * CFG.map.getMapBG().getMapScale(), Pixmap.Format.LuminanceAlpha);
                pixmap.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));

                for(int y = 0; y < pixmap.getHeight(); ++y) {
                    for(int x = 0; x < pixmap.getWidth(); ++x) {
                        boolean add;
                        if (CFG.game.pathContains(this.iProvinceID, this.getMinX() + x, this.getMinY() + y)) {
                            add = true;

                            for(int a = 0; a < this.getNeighboringProvincesSize(); ++a) {
                                if (this.iProvinceID > this.getNeighboringProvinces(a) && CFG.game.pathContains(this.getNeighboringProvinces(a), this.getMinX() + x, this.getMinY() + y)) {
                                    add = false;
                                }
                            }

                            if (add) {
                                pixmap.drawPixel(x, y);
                            }
                        } else {
                            add = false;
                            boolean check = false;
                            if (CFG.game.pathContains(this.iProvinceID, this.getMinX() + x + 1, this.getMinY() + y)) {
                                check = true;
                            }

                            if (CFG.game.pathContains(this.iProvinceID, this.getMinX() + x, this.getMinY() + y + 1)) {
                                check = true;
                            }

                            if (CFG.game.pathContains(this.iProvinceID, this.getMinX() + x - 1, this.getMinY() + y)) {
                                check = true;
                            }

                            if (CFG.game.pathContains(this.iProvinceID, this.getMinX() + x, this.getMinY() + y - 1)) {
                                check = true;
                            }

                            if (check) {
                                boolean edn = false;

                                int a;
                                for(a = 0; a < this.getNeighboringProvincesSize(); ++a) {
                                    if (CFG.game.pathContains(this.getNeighboringProvinces(a), this.getMinX() + x, this.getMinY() + y)) {
                                        edn = true;
                                        break;
                                    }
                                }

                                if (!edn) {
                                    for(a = 0; a < this.getNeighboringProvincesSize(); ++a) {
                                        if (this.iProvinceID > this.getNeighboringProvinces(a)) {
                                            if (CFG.game.pathContains(this.getNeighboringProvinces(a), this.getMinX() + x + 1, this.getMinY() + y)) {
                                                add = true;
                                            } else if (CFG.game.pathContains(this.getNeighboringProvinces(a), this.getMinX() + x, this.getMinY() + y + 1)) {
                                                add = true;
                                            } else if (CFG.game.pathContains(this.getNeighboringProvinces(a), this.getMinX() + x - 1, this.getMinY() + y)) {
                                                add = true;
                                            } else if (CFG.game.pathContains(this.getNeighboringProvinces(a), this.getMinX() + x, this.getMinY() + y - 1)) {
                                                add = true;
                                            }
                                        }
                                    }

                                    if (add) {
                                        pixmap.drawPixel(x, y);
                                    }
                                }
                            }
                        }
                    }
                }

                PixmapIO.writeCIM(Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "scales/" + "provinces/" + Integer.parseInt((String)tempL.get(i)) + "/" + this.iProvinceID), pixmap);
                pixmap.dispose();
                CFG.toast.setInView("-- PROVINCE DATA GENERATED " + this.iProvinceID + " --");
            }
        }

        CFG.map.setMapScale(CFG.map.getActiveMapID(), tempMapScaleBefore);
    }

    protected final void updateProvincePort(int nX, int nY) {
        this.provincePort = new Province_Port_Center(nX, nY);
    }

    protected final void setProvinceColor_FoG_Discovery(SpriteBatch oSB) {
        try {
            if (!CFG.getMetProvince(this.getProvinceID())) {
                oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA));
            } else {
                this.setProvinceColor(oSB);
            }
        } catch (NullPointerException var3) {
            this.setProvinceColor(oSB);
        } catch (IndexOutOfBoundsException var4) {
            oSB.setColor(1.0F, 1.0F, 1.0F, 0.0F);
        }

    }

    protected final void setProvinceColor(SpriteBatch oSB) {
        try {
            if (this.iFromCivID >= 0 && this.getCivID() > 0) {
                if (this.updateColorTime) {
                    this.lColorTime = System.currentTimeMillis();
                    this.updateColorTime = false;
                }

                int tempStepID = Math.min((int)(System.currentTimeMillis() - this.lColorTime), 725);
                if (this.iFromCivID == 0) {
                    oSB.setColor((float)CFG.game.getCiv(this.getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(this.getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(this.getCivID()).getB() / 255.0F, CFG.getColorStep(25, CFG.settingsManager.PROVINCE_ALPHA, tempStepID, 725));
                } else {
                    oSB.setColor(CFG.getColorStep(CFG.game.getCiv(this.iFromCivID).getR(), CFG.game.getCiv(this.getCivID()).getR(), tempStepID, 725), CFG.getColorStep(CFG.game.getCiv(this.iFromCivID).getG(), CFG.game.getCiv(this.getCivID()).getG(), tempStepID, 725), CFG.getColorStep(CFG.game.getCiv(this.iFromCivID).getB(), CFG.game.getCiv(this.getCivID()).getB(), tempStepID, 725), (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F);
                }

                if (this.lColorTime + 725L <= System.currentTimeMillis()) {
                    this.iFromCivID = -1;
                }

                CFG.setRender_3(true);
            } else {
                this.setCivilizationProvinceColor(oSB, this.getCivID());
            }
        } catch (IndexOutOfBoundsException var3) {
            oSB.setColor(1.0F, 1.0F, 1.0F, 0.0F);
        }

    }

    protected final void drawProvince_ActiveProvince(SpriteBatch oSB) {
        if (this.iContinentID == CFG.map.getMapContinents().getOceanContinentID()) {
            this.provinceBG.draw(oSB, this.iTranslateProvincePosX + this.iMinX + this.iMinX * CFG.map.getMapBG().getMapScale() - this.iMinX, CFG.map.getMapCoordinates().getPosY() + this.iMinY + this.iMinY * CFG.map.getMapBG().getMapScale() - this.iMinY + this.provinceBG.getHeight() * CFG.map.getMapBG().getMapScale() - this.provinceBG.getHeight(), (float)CFG.map.getMapBG().getMapScale());
        } else {
            this.drawLandProvince(oSB);
        }

    }

    protected final void drawLandProvince(SpriteBatch oSB) {
        this.provinceBG.draw(oSB, this.iTranslateProvincePosX + this.iMinX * CFG.map.getMapBG().getMapScale(), CFG.map.getMapCoordinates().getPosY() + this.iMinY * CFG.map.getMapBG().getMapScale());
    }

    protected final void drawOccupiedProvince(SpriteBatch oSB) {
        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, (float)CFG.settingsManager.OCCUPIED_PROVINCE_ALPHA / 255.0F));
        AoCGame.shaderAlpha2.setUniformf("u_maskScale", CFG.settingsManager.OCCUPIED_STRIPES_SIZE * Math.max((float)this.provinceBG.getWidth() / (float)ImageManager.getImage(Images.patt2).getWidth(), (float)this.provinceBG.getHeight() / (float)ImageManager.getImage(Images.patt2).getHeight()));
        this.provinceBG.getTexture().bind(1);
        Gdx.gl.glActiveTexture(33984);
        ImageManager.getImage(Images.patt3).draw3(oSB, this.iTranslateProvincePosX + this.iMinX * CFG.map.getMapBG().getMapScale(), CFG.map.getMapCoordinates().getPosY() + this.iMinY * CFG.map.getMapBG().getMapScale() - ImageManager.getImage(Images.patt3).getHeight(), this.provinceBG.getWidth(), this.provinceBG.getHeight());
        oSB.flush();
    }

    protected final void drawWastelandProvince(SpriteBatch oSB) {
        oSB.setColor(this.getWastelandColor(CFG.settingsManager.PROVINCE_ALPHA_WASTELAND));
        this.provinceBG.draw(oSB, this.iTranslateProvincePosX + this.iMinX * CFG.map.getMapBG().getMapScale(), CFG.map.getMapCoordinates().getPosY() + this.iMinY * CFG.map.getMapBG().getMapScale());
    }

    protected final void drawWastelandProvince_PeaceTreaty(SpriteBatch oSB) {
        oSB.setColor(this.getWastelandColor(CFG.settingsManager.PROVINCE_ALPHA_WASTELAND * 0.25F));
        this.provinceBG.draw(oSB, this.iTranslateProvincePosX + this.iMinX * CFG.map.getMapBG().getMapScale(), CFG.map.getMapCoordinates().getPosY() + this.iMinY * CFG.map.getMapBG().getMapScale());
    }

    protected final void drawWastelandProvince(SpriteBatch oSB, float nAlpha) {
        oSB.setColor(this.getWastelandColor(nAlpha));
        this.provinceBG.draw(oSB, this.iTranslateProvincePosX + this.iMinX * CFG.map.getMapBG().getMapScale(), CFG.map.getMapCoordinates().getPosY() + this.iMinY * CFG.map.getMapBG().getMapScale());
    }

    protected final void drawProvinceFlag(SpriteBatch oSB) {
        if (this.getDrawProvince()) {
            oSB.setShader(AoCGame.shaderAlpha);
            this.provinceBG.getTexture().bind(2);
            CFG.game.getCiv(this.getCivID()).getFlag().getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            this.drawLandProvince(oSB);
            oSB.setShader(AoCGame.defaultShader);
        }

    }

    protected final void drawProvinceFlag_CreateRandomGame(SpriteBatch oSB, int nPlayerID) {
        if (this.getDrawProvince()) {
            oSB.setShader(AoCGame.shaderAlpha);
            this.provinceBG.getTexture().bind(2);
            CFG.randomGameManager.getPlayer(nPlayerID).getFlag().getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            this.drawLandProvince(oSB);
            oSB.setShader(AoCGame.defaultShader);
        }

    }

    protected final void drawProvinceInfo(SpriteBatch oSB, float nScale) {
        this.drawProvincePort(oSB, nScale);
    }

    protected final void drawProvincePort(SpriteBatch oSB, float nScale) {
        if (this.getLevelOfPort() > 0) {
            this.provincePort.draw(oSB, (int)((float)(this.getCenterX() + this.getTranslateProvincePosX()) * nScale), (int)((float)(this.getCenterY() + CFG.map.getMapCoordinates().getPosY()) * nScale), nScale);
        }

    }

    protected final void drawProvinceBorder_Timeline(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Integer)CFG.timelapseManager.timelineOwners.get(this.getProvinceID())).equals(CFG.timelapseManager.timelineOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()))) {
                oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getDashedImage(), 0, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_Timeline_Only_CivilizationBorder(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (!((Integer)CFG.timelapseManager.timelineOwners.get(this.getProvinceID())).equals(CFG.timelapseManager.timelineOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()))) {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_PeaceTreaty(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceID())).iCivID == ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())).iCivID) {
                oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getDashedImage(), 0, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_PeaceTreaty_Only_CivilizationBorder(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceID())).iCivID != ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())).iCivID) {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_PeaceTreaty_FogOfWarDiscovery(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceID())).iCivID == ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())).iCivID) {
                    oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getDashedImage(), 0, this.iTranslateProvincePosX);
                } else {
                    oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
                }
            } else if (this.getWasteland() < 0 && CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getWasteland() < 0) {
                if (!CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                    oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_33, 0, this.iTranslateProvincePosX);
                } else {
                    oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
                }
            } else {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_PeaceTreaty_FogOfWarDiscovery_Only_CivilizationBorder(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceID())).iCivID != ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())).iCivID) {
                    oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
                }
            } else if (this.getWasteland() < 0 && CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getWasteland() < 0) {
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                    oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
                }
            } else {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_PeaceTreaty_Wasteland(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (this.getWasteland() < 0 || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getWasteland() < 0) {
                if (this.getWasteland() < 0 && CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getWasteland() < 0) {
                    if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceID())).iCivID == ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())).iCivID) {
                        oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                        ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getDashedImage(), 0, this.iTranslateProvincePosX);
                    } else {
                        oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                        ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
                    }
                } else {
                    oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
                }
            }
        }

    }

    protected final void drawProvinceBorder(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
        }

    }

    protected final void drawProvinceBorder_OnlyCivilizationBorder(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder()) {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_OnlyCivilizationBorder_Capitals(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (!((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() && !this.getIsCapital() && !CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getIsCapital() && CFG.game.getActiveProvinceID() != this.getProvinceID() && CFG.game.getActiveProvinceID() != ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                if (this.getCivID() == 0 || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID() == 0) {
                    oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_33, 0, this.iTranslateProvincePosX);
                }
            } else {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_OnlyCivilizationBorder_Capitals_FogOfWarDiscoveryWasteland(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (!((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() && !this.getIsCapital() && !CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getIsCapital() && CFG.game.getActiveProvinceID() != this.getProvinceID() && CFG.game.getActiveProvinceID() != ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                if (this.getCivID() == 0 || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID() == 0) {
                    oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_33, 0, this.iTranslateProvincePosX);
                }
            } else {
                oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_33, 0, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_OnlyCivilizationBorder_Capitals_FogOfWarDiscovery(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                if (!((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() && !this.getIsCapital() && !CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getIsCapital() && CFG.game.getActiveProvinceID() != this.getProvinceID() && CFG.game.getActiveProvinceID() != ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                    if (this.getCivID() == 0 || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID() == 0) {
                        oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                        ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_33, 0, this.iTranslateProvincePosX);
                    }
                } else {
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
                }
            } else if (!CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_33, 0, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
            }
        }

    }

    protected final void drawProvinceBorder_TerrainMode(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (this.iTerrainTypeID == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getTerrainTypeID()) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_ContinentMode(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (this.getContinent() == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getContinent()) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_ContinentMode_FogOfWarDiscovery(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) && this.getContinent() == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getContinent()) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else if (!CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_RegionMode(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (this.getRegion() == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getRegion()) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_RegionMode_FogOfWarDiscovery(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) && this.getRegion() == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getRegion()) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else if (!CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID()) && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID())) {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            } else {
                oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_ContinentModeWasteland(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder()) {
                if (this.getContinent() == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getContinent()) {
                    oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
                } else {
                    oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
                }
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_RegionModeWasteland(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder()) {
                if (this.getRegion() == CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getRegion()) {
                    oSB.setColor(0.04F, 0.04F, 0.04F, 0.39215687F);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
                } else {
                    oSB.setColor(0.0F, 0.0F, 0.0F, 0.55F);
                    ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.fTerrainMode_LinePercentage / 100.0F, this.iTranslateProvincePosX);
                }
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_CreateRandomGame(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (CFG.game.getActiveProvinceID() != this.getProvinceID() && CFG.game.getActiveProvinceID() != ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID() && CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getWasteland() < 0) {
                oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder(oSB, Images.line_32, 0, this.iTranslateProvincePosX);
            } else {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_CreateRandomGameWasteland(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() || CFG.game.getActiveProvinceID() == this.getProvinceID() || CFG.game.getActiveProvinceID() == ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_PrintAMap(SpriteBatch oSB) {
        oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));

        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        for(i = 0; i < this.iProvinceBordersLandBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersLandBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder(oSB, this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorderInStartGame(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder()) {
                oSB.setColor(0.0F, 0.0F, 0.0F, 1.0F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.startTheGameData.getStraightLinePercentage(), this.iTranslateProvincePosX);
            } else {
                oSB.setColor(0.04F, 0.04F, 0.04F, 0.627451F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawDashedBorder_PercentageWidth(oSB, Images.line_32, 0, CFG.startTheGameData.getDashedLinePercentage(), this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(0.94F, 0.94F, 0.95F, 0.07F);

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.startTheGameData.getStraightLinePercentage(), this.iTranslateProvincePosX);
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorderInStartGame_Wasteland(SpriteBatch oSB) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder()) {
                oSB.setColor(0.0F, 0.0F, 0.0F, 1.0F);
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawStraightBorder_PercentageWidth(oSB, CFG.startTheGameData.getStraightLinePercentage(), this.iTranslateProvincePosX);
            }
        }

        oSB.setColor(Color.WHITE);
    }

    protected final void drawProvinceBorder_NextPlayerTurn(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() || this.getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
        }

    }

    protected final void drawProvinceBorder_CivilizationView(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() || this.getCivID() != Menu_InGame_CivilizationView.iCivID || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID() != Menu_InGame_CivilizationView.iCivID) {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
        }

    }

    protected final void drawProvinceBorder_LoadAI_RTO(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() || !CFG.game.getCiv(this.getCivID()).getControlledByPlayer() || !CFG.game.getCiv(CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID()).getControlledByPlayer()) {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
        }

    }

    protected final void drawProvinceBorder_LoadAI_RTO_FogOfWarDiscovery(SpriteBatch oSB) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (((Province_Border)this.lProvinceBordersLandByLand.get(i)).getIsCivilizationBorder() || this.getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getProvince(((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                ((Province_Border)this.lProvinceBordersLandByLand.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
            }
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).drawProvince_Border.draw(oSB, this.iTranslateProvincePosX);
        }

    }

    protected final void draw(SpriteBatch oSB, int nPosX, int nPosY, float scale, int nAlpha) {
        this.setCivilizationProvinceColor(oSB, ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID(), (float)nAlpha / 255.0F);
        this.provinceBG.draw(oSB, nPosX + this.iMinX + (int)Math.floor((double)((float)this.iMinX * scale * (float)CFG.map.getMapBG().getMapScale())) - this.iMinX, nPosY + this.iMinY + (int)Math.floor((double)((float)this.iMinY * scale * (float)CFG.map.getMapBG().getMapScale())) - this.iMinY + (int)((float)this.provinceBG.getHeight() * scale) - this.provinceBG.getHeight(), scale);
    }

    protected final void draw_FogOfWarDiscovery(SpriteBatch oSB, int nPosX, int nPosY, float scale, int nAlpha) {
        try {
            if (CFG.getMetProvince(this.getProvinceID())) {
                this.setCivilizationProvinceColor(oSB, ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID(), (float)nAlpha / 255.0F);
                this.provinceBG.draw(oSB, nPosX + this.iMinX + (int)Math.floor((double)((float)this.iMinX * scale * (float)CFG.map.getMapBG().getMapScale())) - this.iMinX, nPosY + this.iMinY + (int)Math.floor((double)((float)this.iMinY * scale * (float)CFG.map.getMapBG().getMapScale())) - this.iMinY + (int)((float)this.provinceBG.getHeight() * scale) - this.provinceBG.getHeight(), scale);
            }
        } catch (NullPointerException var7) {
            this.draw(oSB, nPosX, nPosY, scale, nAlpha);
        }

    }

    protected final void drawWasteland(SpriteBatch oSB, int nPosX, int nPosY, float scale, int nAlpha) {
        oSB.setColor(this.getWastelandColor((float)nAlpha / 255.0F));
        this.provinceBG.draw(oSB, nPosX + this.iMinX + (int)Math.floor((double)((float)this.iMinX * scale * (float)CFG.map.getMapBG().getMapScale())) - this.iMinX, nPosY + this.iMinY + (int)Math.floor((double)((float)this.iMinY * scale * (float)CFG.map.getMapBG().getMapScale())) - this.iMinY + (int)((float)this.provinceBG.getHeight() * scale) - this.provinceBG.getHeight(), scale);
    }

    private final Color getWastelandColor(float fAlpha) {
        return new Color(CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.getR() - 0.0627F * (float)this.getWasteland(), CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.getG() - 0.0529F * (float)this.getWasteland(), CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.getB() - 0.0443F * (float)this.getWasteland(), fAlpha);
    }

    protected final void setCivilizationProvinceColor(SpriteBatch oSB, int nCivID) {
        this.setCivilizationProvinceColor(oSB, nCivID, nCivID == 0 ? 0.039215688F : (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F);
    }

    protected final void setCivilizationProvinceColor(SpriteBatch oSB, int nCivID, float nAlpha) {
        oSB.setColor(new Color((float)CFG.game.getCiv(nCivID).getR() / 255.0F, (float)CFG.game.getCiv(nCivID).getG() / 255.0F, (float)CFG.game.getCiv(nCivID).getB() / 255.0F, nAlpha));
    }

    protected final void updateDrawArmy() {
        try {
            if (CFG.FOG_OF_WAR > 0 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getFogOfWar(this.getProvinceID()) && CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(this.getProvinceID()).getCivID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) < 44.0F) {
                if (this.getIsCapital()) {
                    if (CFG.FOG_OF_WAR == 2) {
                        try {
                            if (CFG.getMetProvince(this.getProvinceID())) {
                                if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                                    if (this.getLevelOfArmoury() > 0) {
                                        this.drawArmy = new Province_DrawArmy() {
                                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                                CFG.game.drawProvinceFlag_Capital_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                            }
                                        };
                                    } else {
                                        this.drawArmy = new Province_DrawArmy() {
                                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                                CFG.game.drawProvinceFlag_Capital_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                            }
                                        };
                                    }
                                } else if (this.getLevelOfFort() > 0) {
                                    if (this.getLevelOfArmoury() > 0) {
                                        this.drawArmy = new Province_DrawArmy() {
                                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                                CFG.game.drawProvinceFlag_Capital_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                            }
                                        };
                                    } else {
                                        this.drawArmy = new Province_DrawArmy() {
                                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                                CFG.game.drawProvinceFlag_Capital_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                            }
                                        };
                                    }
                                } else if (this.getLevelOfWatchTower() > 0) {
                                    if (this.getLevelOfArmoury() > 0) {
                                        this.drawArmy = new Province_DrawArmy() {
                                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                                CFG.game.drawProvinceFlag_Capital_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                            }
                                        };
                                    } else {
                                        this.drawArmy = new Province_DrawArmy() {
                                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                                CFG.game.drawProvinceFlag_Capital_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                            }
                                        };
                                    }
                                } else if (this.getLevelOfArmoury() > 0) {
                                    this.drawArmy = new Province_DrawArmy() {
                                        public void drawArmy(SpriteBatch oSB, float nScale) {
                                            CFG.game.drawProvinceFlag_Capital_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                        }
                                    };
                                } else {
                                    this.drawArmy = new Province_DrawArmy() {
                                        public void drawArmy(SpriteBatch oSB, float nScale) {
                                            CFG.game.drawProvinceFlag_Capital(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                        }
                                    };
                                }
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                    }
                                };
                            }
                        } catch (NullPointerException var2) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceFlag_Capital_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceFlag_Capital_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceFlag_Capital_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceFlag_Capital_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceFlag_Capital_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceFlag_Capital_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceFlag_Capital_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceFlag_Capital(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (CFG.FOG_OF_WAR == 2 && !CFG.getMetProvince(this.getProvinceID())) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                        }
                    };
                } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmy_TowerFort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmy_TowerFort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmy_Fort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmy_Fort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmy_Tower_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmy_Tower_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmy_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                        }
                    };
                }
            } else if (this.getSeaProvince()) {
                if (this.getProvinceID() == CFG.game.getActiveProvinceID()) {
                    this.updateArmyWidth_ALL();
                    if (this.getProvinceArmyBoxes() == null) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                int tCenterX = (int)((float)(Province.this.getCenterX() + Province.this.getShiftX() + Province.this.getTranslateProvincePosX()) * nScale);
                                int tCenterY = (int)((float)(Province.this.getCenterY() + Province.this.getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
                                CFG.game.drawProvinceArmy_Sea(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_TEXT_SEA_ACTIVE, nScale, tCenterX, tCenterY);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                int tCenterXx = 0;
                                int tCenterYx = 0;
                                Point_XY tempCenter = CFG.game.updateSeaProvince_CenterArmyPostion(Province.this.iProvinceID, nScale);
                                int tCenterX = tempCenter.getPosX();
                                int tCenterY = tempCenter.getPosY();
                                CFG.game.drawProvinceArmy_Sea(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_TEXT_SEA_ACTIVE, nScale, tCenterX, tCenterY);
                            }
                        };
                    }
                } else if (this.getProvinceArmyBoxes() == null) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            int tCenterX = (int)((float)(Province.this.getCenterX() + Province.this.getShiftX() + Province.this.getTranslateProvincePosX()) * nScale);
                            int tCenterY = (int)((float)(Province.this.getCenterY() + Province.this.getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
                            CFG.game.drawProvinceArmy_Sea(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_TEXT_SEA, nScale, tCenterX, tCenterY);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            int tCenterXx = 0;
                            int tCenterYx = 0;
                            Point_XY tempCenter = CFG.game.updateSeaProvince_CenterArmyPostion(Province.this.iProvinceID, nScale);
                            int tCenterX = tempCenter.getPosX();
                            int tCenterY = tempCenter.getPosY();
                            CFG.game.drawProvinceArmy_Sea(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_TEXT_SEA, nScale, tCenterX, tCenterY);
                        }
                    };
                }
            } else if (this.getIsCapital()) {
                if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && this.getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(this.getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()) {
                    if (this.iProvinceID == CFG.game.getActiveProvinceID()) {
                        this.updateArmyWidth_ALL();
                        if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                            if (this.getLevelOfArmoury() > 0) {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            }
                        } else if (this.getLevelOfFort() > 0) {
                            if (this.getLevelOfArmoury() > 0) {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            }
                        } else if (this.getLevelOfWatchTower() > 0) {
                            if (this.getLevelOfArmoury() > 0) {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            }
                        } else if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }
                        };
                    }
                } else if (this.getCivID() > 0 && (int)CFG.game.getCivRelation_OfCivB(this.getCivID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) == -100) {
                    this.updateArmyWidth_ALL();
                    if (this.iProvinceID == CFG.game.getActiveProvinceID()) {
                        if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                            if (this.getLevelOfArmoury() > 0) {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            }
                        } else if (this.getLevelOfFort() > 0) {
                            if (this.getLevelOfArmoury() > 0) {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            }
                        } else if (this.getLevelOfWatchTower() > 0) {
                            if (this.getLevelOfArmoury() > 0) {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            } else {
                                this.drawArmy = new Province_DrawArmy() {
                                    public void drawArmy(SpriteBatch oSB, float nScale) {
                                        CFG.game.drawProvinceArmyWithFlag_Capital_Active_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                    }
                                };
                            }
                        } else if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    }
                } else if (this.iProvinceID == CFG.game.getActiveProvinceID()) {
                    this.updateArmyWidth_ALL();
                    if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Capital_Active_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Active_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Active(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_ARMY_TEXT_CAPITAL_ACTIVE, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_TowerFortArmoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Capital_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Capital_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Capital(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_CAPITAL_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                }
            } else if (this.iProvinceID == CFG.game.getActiveProvinceID()) {
                this.updateArmyWidth_ALL();
                if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Active_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Active_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Active_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Active_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Active_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Active_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Active_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Active(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                }
            } else if (this.iProvinceID == CFG.chosenProvinceID) {
                if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
                        }
                    };
                }
            } else if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == this.getCivID()) {
                if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                } else if (this.getCivID() == 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                }
            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && this.getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(this.getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()) {
                if (this.getCivsSize() > 1) {
                    if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_TowerFort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_TowerFort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Fort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Fort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Tower_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Tower_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }

                        }
                    };
                }
            } else if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != this.getCivID() && CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getCiv(this.getCivID()).getPuppetOfCivID()) {
                if (this.getCivsSize() > 1) {
                    if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfFort() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfWatchTower() > 0) {
                        if (this.getLevelOfArmoury() > 0) {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        } else {
                            this.drawArmy = new Province_DrawArmy() {
                                public void drawArmy(SpriteBatch oSB, float nScale) {
                                    CFG.game.drawProvinceArmyWithFlag_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }
                            };
                        }
                    } else if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_TowerFort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_TowerFort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Fort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Fort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Tower_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                if (Province.this.getArmy(0) > 0) {
                                    CFG.game.drawProvinceArmy_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                } else {
                                    CFG.game.drawProvinceArmy_Tower_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                                }

                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG_ALLIANCE, CFG.COLOR_ARMY_TEXT, nScale);
                            }

                        }
                    };
                }
            } else if (this.getCivID() > 0 && (int)CFG.game.getCivRelation_OfCivB(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getCivID()) == -100) {
                if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2, nScale);
                        }
                    };
                }
            } else if (this.getCivsSize() > 1) {
                if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfFort() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfWatchTower() > 0) {
                    if (this.getLevelOfArmoury() > 0) {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    } else {
                        this.drawArmy = new Province_DrawArmy() {
                            public void drawArmy(SpriteBatch oSB, float nScale) {
                                CFG.game.drawProvinceArmyWithFlag_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }
                        };
                    }
                } else if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }
                    };
                }
            } else if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_TowerFort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_TowerFort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                }
            } else if (this.getLevelOfFort() > 0) {
                if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Fort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Fort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                }
            } else if (this.getLevelOfWatchTower() > 0) {
                if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Tower_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Tower_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                }
            } else if (this.getLevelOfArmoury() > 0) {
                this.drawArmy = new Province_DrawArmy() {
                    public void drawArmy(SpriteBatch oSB, float nScale) {
                        if (Province.this.getArmy(0) > 0) {
                            CFG.game.drawProvinceArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }

                    }
                };
            } else if (this.getCivID() == 0) {
                this.drawArmy = new Province_DrawArmy() {
                    public void drawArmy(SpriteBatch oSB, float nScale) {
                        if (Province.this.getArmy(0) > 0) {
                            CFG.game.drawProvinceArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }

                    }
                };
            } else {
                this.drawArmy = new Province_DrawArmy() {
                    public void drawArmy(SpriteBatch oSB, float nScale) {
                        if (Province.this.getArmy(0) > 0) {
                            CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }

                    }
                };
            }
        } catch (IndexOutOfBoundsException var3) {
            if (this.getLevelOfFort() > 0 && this.getLevelOfWatchTower() > 0) {
                if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_TowerFort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_TowerFort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_TowerFort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_TowerFort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                }
            } else if (this.getLevelOfFort() > 0) {
                if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Fort_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Fort_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Fort(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Fort_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                }
            } else if (this.getLevelOfWatchTower() > 0) {
                if (this.getLevelOfArmoury() > 0) {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Tower_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Tower_NoArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                } else {
                    this.drawArmy = new Province_DrawArmy() {
                        public void drawArmy(SpriteBatch oSB, float nScale) {
                            if (Province.this.getArmy(0) > 0) {
                                CFG.game.drawProvinceArmy_Tower(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            } else {
                                CFG.game.drawProvinceArmy_Tower_NoArmy(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                            }

                        }
                    };
                }
            } else if (this.getLevelOfArmoury() > 0) {
                this.drawArmy = new Province_DrawArmy() {
                    public void drawArmy(SpriteBatch oSB, float nScale) {
                        if (Province.this.getArmy(0) > 0) {
                            CFG.game.drawProvinceArmy_Armoury(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }

                    }
                };
            } else {
                this.drawArmy = new Province_DrawArmy() {
                    public void drawArmy(SpriteBatch oSB, float nScale) {
                        if (Province.this.getArmy(0) > 0) {
                            CFG.game.drawProvinceArmyWithFlag(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
                        }

                    }
                };
            }
        }

    }

    protected final void updateDrawArmy_ShowsIDs() {
        this.drawArmy = new Province_DrawArmy() {
            public void drawArmy(SpriteBatch oSB, float nScale) {
                CFG.game.drawProvinceID(oSB, Province.this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
            }
        };
    }

    protected final void drawArmy(SpriteBatch oSB, float nScale) {
        this.drawArmy.drawArmy(oSB, nScale);
    }

    protected final void drawBuildings(SpriteBatch oSB, float nScale) {
        int tCenterX = (int)((float)(this.getCenterX() + this.getShiftX() + this.getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((float)(this.getCenterY() + this.getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        CFG.game.drawProvinceBuildings(oSB, tCenterX, tCenterY, this.iProvinceID);
    }

    protected final void drawArmy_SetUpArmy_Sea(SpriteBatch oSB, float nScale) {
        int tCenterX = (int)((float)(this.getCenterX() + this.getShiftX() + this.getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((float)(this.getCenterY() + this.getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        CFG.game.drawProvinceArmy_Sea(oSB, this.iProvinceID, CFG.COLOR_ARMY_TEXT_SEA, nScale, tCenterX, tCenterY);
    }

    protected final void drawArmy_SetUpArmy(SpriteBatch oSB, float nScale) {
        if (this.getCivsSize() <= 1 && !this.getIsCapital()) {
            if (this.getArmy(0) > 0) {
                CFG.game.drawProvinceArmy(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
            }
        } else {
            CFG.game.drawProvinceArmyWithFlag(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
        }

    }

    protected final void drawGrowthRate(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_GrowthRate(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawTechnologyLevels(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_TechnologyLevels(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawPotential(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_Potential(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawDanger(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_Danger(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawHappiness(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_Happiness(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawStartingMoney(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_StartingMoney(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawArmyPosition_Active(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_ArmyPosition(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG_ACTIVE, CFG.COLOR_ARMY_TEXT_ACTIVE, nScale);
    }

    protected final void drawArmyPosition(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_ArmyPosition(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawArmyPositionSea(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_ArmyPosition(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG_SEA, CFG.COLOR_ARMY_TEXT_SEA, nScale);
    }

    protected final void drawArmyOptimizationRegions(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_OptimizationRegions(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final void drawArmySeaProvincesLevels(SpriteBatch oSB, float nScale) {
        CFG.game.drawProvince_SeaProvincesLevels(oSB, this.iProvinceID, CFG.COLOR_ARMY_BG, CFG.COLOR_TEXT_MODIFIER_NEUTRAL2, nScale);
    }

    protected final int getTrueOwnerOfProvince() {
        return this.saveProvinceData.iTrueOwnerOfProvince;
    }

    protected final void setTrueOwnerOfProvince(int iTrueOwnerOfProvince) {
        this.saveProvinceData.iTrueOwnerOfProvince = iTrueOwnerOfProvince;
    }

    protected final boolean isOccupied() {
        return this.getCivID() != this.getTrueOwnerOfProvince();
    }

    protected final int getCivID() {
        try {
            return ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID();
        } catch (IndexOutOfBoundsException var2) {
            CFG.exceptionStack(var2);
            return 0;
        }
    }

    protected final int getCivID(int i) {
        try {
            return ((Province_Army)this.saveProvinceData.lArmies.get(i)).getCivID();
        } catch (IndexOutOfBoundsException var3) {
            return 0;
        }
    }

    protected final void setCivID_Just(int nCivID) {
        ((Province_Army)this.saveProvinceData.lArmies.get(0)).setCivID(nCivID);
        this.saveProvinceData.iTrueOwnerOfProvince = nCivID;
        this.iFromCivID = -1;
    }

    protected final void setCivID_LoadScenario(int nCivID) {
        if (nCivID != 0) {
            this.iFromCivID = ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID();
            this.updateColorTime = true;
        }

        ((Province_Army)this.saveProvinceData.lArmies.get(0)).setCivID(nCivID);
        this.saveProvinceData.iTrueOwnerOfProvince = nCivID;
    }

    protected final void setCivID(int nCivID, boolean conquered) {
        this.setCivID(nCivID, conquered, true);
    }

    protected final void setCivID(int nCivID, boolean conquered, boolean isInGame) {
        if (nCivID != this.getCivID()) {
            Gdx.app.log("AoC", "SET CIVID: 111");

            try {
                if (CFG.FOG_OF_WAR == 2 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceID())) {
                    this.iFromCivID = -1;
                } else if (nCivID != 0) {
                    this.iFromCivID = ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID();
                    this.updateColorTime = true;
                }
            } catch (NullPointerException var8) {
                if (nCivID != 0) {
                    this.iFromCivID = ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID();
                    this.updateColorTime = true;
                }

                if (CFG.LOGS) {
                    CFG.exceptionStack(var8);
                }
            }

            Gdx.app.log("AoC", "SET CIVID: 222");
            int oldCivID = ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID();
            ((Province_Army)this.saveProvinceData.lArmies.get(0)).setCivID(nCivID);
            if (oldCivID != 0) {
                CFG.game.getCiv(oldCivID).removeProvince(this.getProvinceID());
                CFG.game.getCiv(oldCivID).setUpdateRegions(true);
            } else {
                this.setTrueOwnerOfProvince(nCivID);
            }

            Gdx.app.log("AoC", "SET CIVID: 333");
            if (nCivID != 0) {
                CFG.game.getCiv(nCivID).addProvince(this.getProvinceID());
                CFG.game.getCiv(nCivID).setUpdateRegions(true);
            }

            Gdx.app.log("AoC", "SET CIVID: 444");
            this.updateProvinceBorder_OwnerAnimation();
            Gdx.app.log("AoC", "SET CIVID: 555");
            this.updateDrawArmy();
            Gdx.app.log("AoC", "SET CIVID: 666");
            int i;
            if (isInGame) {
                if (oldCivID != this.getCivID() && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(oldCivID).getIdeologyID()).REVOLUTIONARY) {
                    if (CFG.game.getCiv(oldCivID).getNumOfProvinces() != 0) {
                        CFG.game.getCiv(oldCivID).civGameData.iRevolt_LastTurnLostProvince = Game_Calendar.TURN_ID;
                    } else {
                        if (CFG.game.getCiv(oldCivID).getCapitalProvinceID() >= 0 && CFG.game.getCiv(oldCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(oldCivID).getCapitalProvinceID()).getCivID() != oldCivID) {
                            for(i = 0; i < CFG.game.getProvince(CFG.game.getCiv(oldCivID).getCapitalProvinceID()).getCitiesSize(); ++i) {
                                if (CFG.game.getProvince(CFG.game.getCiv(oldCivID).getCapitalProvinceID()).getCity(i).getCityLevel() == CFG.getEditorCityLevel(0)) {
                                    CFG.game.getProvince(CFG.game.getCiv(oldCivID).getCapitalProvinceID()).getCity(i).setCityLevel(CFG.getEditorCityLevel(1));
                                }
                            }
                        }

                        for(i = 0; i < CFG.game.getCivsSize(); ++i) {
                            if (CFG.game.getCivsAtWar(oldCivID, i)) {
                                CFG.game.whitePeace(oldCivID, i);
                            }
                        }
                    }
                }

                if (CFG.FOG_OF_WAR > 0) {
                    List updateView = this.getUpdateView_SetCivID(oldCivID);

                    for(i = 0; i < updateView.size(); ++i) {
                        this.updateFogOfWar((Integer)updateView.get(i));
                    }
                }

                CFG.timelapseManager.addChange(this.getProvinceID(), nCivID, this.isOccupied());
            }

            Gdx.app.log("AoC", "SET CIVID: 777");
            if (conquered && oldCivID != this.getCivID()) {
                this.saveProvinceData.neighbooringProvinceOfCivWasLost = 0;
                if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(oldCivID).getIdeologyID()).REVOLUTIONARY) {
                    this.setHappiness(this.getHappiness() * (0.96241F - (float)CFG.oR.nextInt(92) / 1000.0F));
                } else {
                    this.saveProvinceData.wasConquered = (byte)(this.getCivID() == this.getTrueOwnerOfProvince() ? 3 : 4);
                    --CFG.game.getCiv(oldCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn;
                    ++CFG.game.getCiv(this.getCivID()).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn;
                    if (this.isOccupied()) {
                        ++CFG.game.getCiv(this.getCivID()).civGameData.iNumOfConqueredProvinces;
                        if (CFG.game.getCiv(this.getCivID()).getControlledByPlayer()) {
                            for(i = 0; i < CFG.game.getPlayersSize(); ++i) {
                                if (CFG.game.getPlayer(i).getCivID() == this.getCivID()) {
                                    try {
                                        CFG.game.getPlayer(i).statistics_Civ_GameData.setConqueredProvinces(CFG.game.getPlayer(i).statistics_Civ_GameData.getConqueredProvinces() + 1);
                                    } catch (NullPointerException var7) {
                                        CFG.game.getPlayer(i).statistics_Civ_GameData = CFG.serviceRibbon_Manager.loadStatistics_Civ(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getCivTag());
                                        if (CFG.LOGS) {
                                            CFG.exceptionStack(var7);
                                        }
                                    }

                                    if (CFG.serviceRibbon_Manager.check_Request_ConquredProvinces(CFG.game.getPlayer(i).statistics_Civ_GameData.getConqueredProvinces())) {
                                        CFG.achievement_Data = new Achievement_Data(CFG.game.getPlayer(i).getCivID(), CFG.game.getPlayer(i).statistics_Civ_GameData.sTag, CFG.langManager.get("ConqueredProvinces") + ": ", "" + CFG.game.getPlayer(i).statistics_Civ_GameData.getConqueredProvinces(), CFG.serviceRibbon_Manager.getRequestProvinces_Level(CFG.game.getPlayer(i).statistics_Civ_GameData.getConqueredProvinces()) - 1);
                                        CFG.achievement_Data = new Achievement_Data(CFG.game.getPlayer(i).getCivID(), CFG.game.getPlayer(i).statistics_Civ_GameData.sTag, CFG.langManager.get("ConqueredProvinces") + ": ", "" + CFG.game.getPlayer(i).statistics_Civ_GameData.getConqueredProvinces(), CFG.serviceRibbon_Manager.getRequestProvinces_Level(CFG.game.getPlayer(i).statistics_Civ_GameData.getConqueredProvinces()) - 1);
                                        SaveManager.saveRequest = true;
                                    }
                                    break;
                                }
                            }

                            //if (CFG.isDesktop() && AoCGame.steamGame != null) {
                            //    AoCGame.steamGame.uploadScore();
                            //}
                        }
                    }

                    for(i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                        if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID() == oldCivID) {
                            this.saveProvinceData.neighbooringProvinceOfCivWasLost = 2;
                        }
                    }
                }
            }

            Gdx.app.log("AoC", "SET CIVID: 888 END");
        }
    }

    private final List getUpdateView_SetCivID(int oldOwner) {
        List tPlayers = new ArrayList();

        for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (CFG.game.getPlayer(i).getCivID() == this.getCivID() || CFG.game.getPlayer(i).getCivID() == CFG.game.getCiv(this.getCivID()).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getPuppetOfCivID() == this.getCivID() || CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID() == CFG.game.getCiv(this.getCivID()).getAllianceID() || CFG.game.getPlayer(i).getCivID() == oldOwner || CFG.game.getPlayer(i).getCivID() == CFG.game.getCiv(oldOwner).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getPuppetOfCivID() == oldOwner || CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID() == CFG.game.getCiv(oldOwner).getAllianceID()) {
                tPlayers.add(i);
            }
        }

        return tPlayers;
    }

    protected final void updateFogOfWar(int nPlayerID) {
        try {
            boolean bProvinceView = false;
            int i;
            boolean nState;
            int j;
            int k;
            if (this.getSeaProvince()) {
                if (CFG.game.haveArmy_FogOfWarCheck(this.getProvinceID(), CFG.game.getPlayer(nPlayerID).getCivID())) {
                    bProvinceView = true;

                    for(i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                        if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getSeaProvince()) {
                            CFG.game.getPlayer(nPlayerID).setFogOfWar(this.getNeighboringProvinces(i), true);
                        }

                        CFG.game.getPlayer(nPlayerID).setMetProvince(this.getNeighboringProvinces(i), true);
                        CFG.game.getProvince(this.getNeighboringProvinces(i)).updateProvinceBorder();

                        for(k = 0; k < CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvincesSize(); ++k) {
                            CFG.game.getPlayer(nPlayerID).setMetProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k), true);
                            CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k)).updateProvinceBorder();

                            for(j = 0; j < CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k)).getCivsSize(); ++j) {
                                CFG.game.getPlayer(nPlayerID).setMetCivilization(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k)).getCivID(j), true);
                            }
                        }
                    }
                }

                if (!bProvinceView) {
                    for(i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                        if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getSeaProvince()) {
                            nState = CFG.game.haveArmy_FogOfWarCheck(this.getNeighboringProvinces(i), CFG.game.getPlayer(nPlayerID).getCivID());

                            for(j = 0; j < CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvincesSize(); ++j) {
                                if (CFG.game.haveArmy_FogOfWarCheck(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j), CFG.game.getPlayer(nPlayerID).getCivID())) {
                                    nState = true;
                                    break;
                                }
                            }

                            CFG.game.getPlayer(nPlayerID).setFogOfWar_ExtraCheck(this.getNeighboringProvinces(i), nState);
                        }
                    }
                }
            } else {
                i = 0;

                label294:
                while(true) {
                    if (i >= this.getNeighboringProvincesSize()) {
                        i = 0;

                        while(true) {
                            if (i >= this.getNeighboringSeaProvincesSize()) {
                                break label294;
                            }

                            nState = false;
                            if (CFG.game.getPlayer(nPlayerID).getCivID() == this.getCivID() || CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getCiv(this.getCivID()).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() == this.getCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() == CFG.game.getCiv(this.getCivID()).getAllianceID()) {
                                nState = true;
                            } else {
                                for(j = 0; j < CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvincesSize(); ++j) {
                                    if (CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getSeaProvince()) {
                                        for(k = 0; k < CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivsSize(); ++k) {
                                            if (CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID(k) || CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID(k)).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() == CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID(k) || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID(k)).getAllianceID()) {
                                                nState = true;
                                                j = CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvincesSize();
                                                break;
                                            }
                                        }
                                    } else if (CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID() || CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID()).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() == CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvinces(j)).getCivID()).getAllianceID()) {
                                        nState = true;
                                        j = CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).getNeighboringProvincesSize();
                                        break;
                                    }
                                }
                            }

                            if (CFG.game.getPlayer(nPlayerID).getFogOfWar(this.getNeighboringSeaProvinces(i)) != nState) {
                                CFG.game.getPlayer(nPlayerID).setFogOfWar_ExtraCheck(this.getNeighboringSeaProvinces(i), nState);
                                if (nPlayerID == CFG.PLAYER_TURNID) {
                                    CFG.game.getProvince(this.getNeighboringSeaProvinces(i)).updateDrawArmy();
                                }
                            }

                            ++i;
                        }
                    }

                    nState = false;
                    if (CFG.game.getPlayer(nPlayerID).getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID() && CFG.game.getPlayer(nPlayerID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID()).getPuppetOfCivID() && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID() && (CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID()).getAllianceID()) && !CFG.game.haveArmy_FogOfWarCheck(this.getNeighboringProvinces(i), CFG.game.getPlayer(nPlayerID).getCivID())) {
                        for(j = 0; j < CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvincesSize(); ++j) {
                            if (CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j)).getLevelOfWatchTower() > 0 && CFG.game.getProvince(this.getNeighboringProvinces(i)).getLevelOfFort() < 1 && (CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j)).getCivID() || CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j)).getCivID()).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() == CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j)).getCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j)).getCivID()).getAllianceID() || CFG.game.haveArmy_FogOfWarCheck(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(j), CFG.game.getPlayer(nPlayerID).getCivID()))) {
                                nState = true;
                                break;
                            }
                        }
                    } else {
                        if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getLevelOfWatchTower() > 0 && this.getLevelOfFort() < 1) {
                            bProvinceView = true;
                        }

                        nState = true;
                    }

                    if (CFG.game.getPlayer(nPlayerID).getFogOfWar(this.getNeighboringProvinces(i)) != nState) {
                        CFG.game.getPlayer(nPlayerID).setFogOfWar_ExtraCheck(this.getNeighboringProvinces(i), nState);
                        if (nPlayerID == CFG.PLAYER_TURNID) {
                            CFG.game.getProvince(this.getNeighboringProvinces(i)).updateDrawArmy();
                        }
                    }

                    ++i;
                }
            }

            bProvinceView = bProvinceView || CFG.game.getPlayer(nPlayerID).getCivID() == this.getCivID() || CFG.game.getPlayer(nPlayerID).getCivID() == CFG.game.getCiv(this.getCivID()).getPuppetOfCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() == this.getCivID() || CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() == CFG.game.getCiv(this.getCivID()).getAllianceID() || CFG.game.haveArmy_FogOfWarCheck(this.getProvinceID(), CFG.game.getPlayer(nPlayerID).getCivID());
            CFG.game.getPlayer(nPlayerID).setFogOfWar_ExtraCheck(this.getProvinceID(), bProvinceView);
            if (bProvinceView) {
                for(i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                    CFG.game.getPlayer(nPlayerID).setMetProvince(this.getNeighboringProvinces(i), true);
                    CFG.game.getProvince(this.getNeighboringProvinces(i)).updateProvinceBorder();

                    for(k = 0; k < CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvincesSize(); ++k) {
                        CFG.game.getPlayer(nPlayerID).setMetProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k), true);
                        CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k)).updateProvinceBorder();

                        for(j = 0; j < CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k)).getCivsSize(); ++j) {
                            CFG.game.getPlayer(nPlayerID).setMetCivilization(CFG.game.getProvince(CFG.game.getProvince(this.getNeighboringProvinces(i)).getNeighboringProvinces(k)).getCivID(j), true);
                        }
                    }

                    for(k = 0; k < CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivsSize(); ++k) {
                        CFG.game.getPlayer(nPlayerID).setMetCivilization(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(k), true);
                    }
                }
            }
        } catch (IndexOutOfBoundsException var7) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var7);
            }
        } catch (NullPointerException var8) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var8);
            }
        }

    }

    protected final void updateProvinceBorder() {
        int i;
        if (CFG.FOG_OF_WAR == 2) {
            for(i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                try {
                    if (CFG.getMetProvince(this.getProvinceID()) && CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                        if (this.getWasteland() < 0 && CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() < 0) {
                            if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                                this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
                            } else {
                                CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
                            }
                        } else {
                            this.setWasteland(this.getWasteland());
                            CFG.game.getProvince(this.getNeighboringProvinces(i)).setWasteland(CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland());
                        }
                    } else if (this.getWasteland() >= 0) {
                        this.setWasteland(this.getWasteland());
                        if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() >= 0) {
                            CFG.game.getProvince(this.getNeighboringProvinces(i)).setWasteland(CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland());
                        }
                    } else if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() < 0) {
                        if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                            this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder(CFG.getMetProvince(this.getProvinceID()) || CFG.getMetProvince(this.getNeighboringProvinces(i)), this.getProvinceID());
                        } else {
                            CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder(CFG.getMetProvince(this.getProvinceID()) || CFG.getMetProvince(this.getNeighboringProvinces(i)), this.getProvinceID());
                        }
                    } else if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                        this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder(CFG.getMetProvince(this.getProvinceID()) || CFG.getMetProvince(this.getNeighboringProvinces(i)), this.getProvinceID());
                    } else {
                        CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder(CFG.getMetProvince(this.getProvinceID()) || CFG.getMetProvince(this.getNeighboringProvinces(i)), this.getProvinceID());
                    }
                } catch (NullPointerException var3) {
                    if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                        this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
                    } else {
                        CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
                    }

                    CFG.exceptionStack(var3);
                }
            }
        } else {
            for(i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                    this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
                } else {
                    CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
                }
            }
        }

    }

    protected final void updateProvinceBorder_OwnerAnimation() {
        for(int i = 0; i < this.getNeighboringProvincesSize(); ++i) {
            if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder_OwnerAnimation(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
            } else {
                CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder_OwnerAnimation(this.getCivID() != CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID(), this.getProvinceID());
            }
        }

    }

    protected final void addCiv(int iCivID, int iArmy) {
        this.saveProvinceData.lArmies.add(new Province_Army(iCivID, iArmy, this.getProvinceID()));
        this.saveProvinceData.iCivsSize = this.saveProvinceData.lArmies.size();
        if (this.getCivsSize() > 1 && iArmy > 0) {
            CFG.game.getCiv(iCivID).addArmyInAnotherProvince(this.getProvinceID());

            for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                if (CFG.game.isPlayerAlly_FogOfWarCheck(CFG.game.getPlayer(i).getCivID(), iCivID)) {
                    this.updateFogOfWar(i);
                }
            }

            this.updateDrawArmy();
        }

    }

    protected final void removeCiv(int i) {
        this.saveProvinceData.lArmies.remove(i);
        this.saveProvinceData.iCivsSize = this.saveProvinceData.lArmies.size();
        this.updateDrawArmy();
    }

    protected final void build_ArmyInAnotherProvince() {
        if (this.getCivsSize() > 1) {
            for(int i = 1; i < this.getCivsSize(); ++i) {
                CFG.game.getCiv(this.getCivID(i)).addArmyInAnotherProvince(this.getProvinceID());
            }
        }

    }

    protected final void addArmy(int iCivID, int iArmy) {
        this.addCiv(iCivID, iArmy);
    }

    protected final void resetArmies(int iArmy) {
        int nCivID = this.getCivID();

        for(int i = 0; i < this.saveProvinceData.lArmies.size(); ++i) {
            CFG.game.getCiv(((Province_Army)this.saveProvinceData.lArmies.get(i)).getCivID()).setNumOfUnits(CFG.game.getCiv(((Province_Army)this.saveProvinceData.lArmies.get(i)).getCivID()).getNumOfUnits() - ((Province_Army)this.saveProvinceData.lArmies.get(i)).getArmy());
        }

        this.saveProvinceData.lArmies.clear();
        this.saveProvinceData.lArmies.add(new Province_Army(nCivID, iArmy, this.getProvinceID()));
        this.saveProvinceData.iCivsSize = this.saveProvinceData.lArmies.size();
        this.saveProvinceData.iDefensivePosition = -1;
    }

    protected final void resetArmies_NewGame(int iArmy) {
        int nCivID = this.getCivID();
        this.saveProvinceData.lArmies.clear();
        this.saveProvinceData.lArmies.add(new Province_Army(nCivID, iArmy, this.getProvinceID()));
        this.saveProvinceData.iCivsSize = this.saveProvinceData.lArmies.size();
        this.saveProvinceData.iDefensivePosition = -1;
    }

    protected final void updateArmy(int iArmy) {
        ((Province_Army)this.saveProvinceData.lArmies.get(0)).setArmy(iArmy, this.getProvinceID());
        this.saveProvinceData.iDefensivePosition = -1;
    }

    protected final void updateArmy(int iCivID, int iArmy) {
        if (iArmy <= 0 && iCivID != ((Province_Army)this.saveProvinceData.lArmies.get(0)).getCivID()) {
            this.removeArmy(iCivID);
        } else {
            for(int i = 0; i < this.saveProvinceData.iCivsSize; ++i) {
                if (((Province_Army)this.saveProvinceData.lArmies.get(i)).getCivID() == iCivID) {
                    ((Province_Army)this.saveProvinceData.lArmies.get(i)).setArmy(iArmy, this.getProvinceID());
                    if (i == 0) {
                        this.saveProvinceData.iDefensivePosition = -1;
                    }

                    return;
                }
            }

            this.addArmy(iCivID, iArmy);
        }
    }

    protected final int getArmyCivID(int nCivID) {
        for(int i = 0; i < this.saveProvinceData.iCivsSize; ++i) {
            if (((Province_Army)this.saveProvinceData.lArmies.get(i)).getCivID() == nCivID) {
                return ((Province_Army)this.saveProvinceData.lArmies.get(i)).getArmy();
            }
        }

        return 0;
    }

    protected final void removeArmy(int iCivID) {
        for(int i = 0; i < this.saveProvinceData.iCivsSize; ++i) {
            if (((Province_Army)this.saveProvinceData.lArmies.get(i)).getCivID() == iCivID) {
                this.removeCiv(i);
                CFG.game.getCiv(iCivID).removeArmyInAnotherProvince(this.iProvinceID);

                for(int j = 0; j < CFG.game.getPlayersSize(); ++j) {
                    if (CFG.game.isPlayerAlly_FogOfWarCheck(CFG.game.getPlayer(j).getCivID(), iCivID)) {
                        this.updateFogOfWar(j);
                    }
                }

                return;
            }
        }

    }

    protected final void removeArmy_ID(int nID) {
        try {
            CFG.game.getCiv(this.getCivID(nID)).removeArmyInAnotherProvince(this.iProvinceID);
            this.removeCiv(nID);
        } catch (IndexOutOfBoundsException var3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var3);
            }
        }

    }

    protected final void addCity(City oCity) {
        this.lCities.add(oCity);
        this.iCitiesSize = this.lCities.size();
    }

    protected final City getCity(int i) {
        return (City)this.lCities.get(i);
    }

    protected final int getCitiesSize() {
        return this.iCitiesSize;
    }

    protected final void clearCities() {
        this.lCities = new ArrayList();
        this.iCitiesSize = 0;
    }

    protected final boolean getDrawCities() {
        return this.drawCities;
    }

    protected final void setDrawCities(boolean drawCities) {
        this.drawCities = drawCities;
    }

    protected final void addMountain(Mountain oMountain) {
        this.lMountains.add(oMountain);
    }

    protected final Mountain getMountain(int i) {
        return (Mountain)this.lMountains.get(i);
    }

    protected final int getMountainsSize() {
        return this.lMountains.size();
    }

    protected final void addWonder(Wonder oWonder) {
        this.lWonders.add(oWonder);
        this.iWondersSize = this.lWonders.size();
    }

    protected final Wonder getWonder(int i) {
        return (Wonder)this.lWonders.get(i);
    }

    protected final int getWonderSize() {
        return this.iWondersSize;
    }

    protected final int getMinX() {
        return this.iMinX * CFG.map.getMapBG().getMapScale();
    }

    protected final int getMaxX() {
        return this.iMaxX * CFG.map.getMapBG().getMapScale();
    }

    protected final int getMinY() {
        return this.iMinY * CFG.map.getMapBG().getMapScale();
    }

    protected final int getMaxY() {
        return this.iMaxY * CFG.map.getMapBG().getMapScale();
    }

    protected final int getPointsX(int i) {
        return (Short)this.lPointsX.get(i) * CFG.map.getMapBG().getMapScale();
    }

    protected final int getPointsY(int i) {
        return (Short)this.lPointsY.get(i) * CFG.map.getMapBG().getMapScale();
    }

    protected final void setPoints(List nPointsX, List nPointsY) {
        this.lPointsX.clear();
        this.lPointsY.clear();

        int i;
        for(i = 0; i < nPointsX.size(); ++i) {
            this.lPointsX.add((Short)nPointsX.get(i));
            this.lPointsY.add((Short)nPointsY.get(i));
        }

        this.iPointsSize = this.lPointsX.size();
        this.iMinX = this.iMaxX = (Short)this.lPointsX.get(0);
        this.iMinY = this.iMaxY = (Short)this.lPointsY.get(0);
        i = 0;

        for(int iSize = this.lPointsX.size(); i < iSize; ++i) {
            if (this.iMinX > (Short)this.lPointsX.get(i)) {
                this.iMinX = (Short)this.lPointsX.get(i);
            }

            if (this.iMaxX < (Short)this.lPointsX.get(i)) {
                this.iMaxX = (Short)this.lPointsX.get(i);
            }

            if (this.iMinY > (Short)this.lPointsY.get(i)) {
                this.iMinY = (Short)this.lPointsY.get(i);
            }

            if (this.iMaxY < (Short)this.lPointsY.get(i)) {
                this.iMaxY = (Short)this.lPointsY.get(i);
            }
        }

    }

    protected final int getCenterX() {
        return this.iCenterX * CFG.map.getMapBG().getMapScale();
    }

    protected final int getCenterY() {
        return this.iCenterY * CFG.map.getMapBG().getMapScale();
    }

    protected final int getCenterX_Real() {
        return this.iCenterX;
    }

    protected final int getCenterY_Real() {
        return this.iCenterY;
    }

    protected final int getShiftX() {
        return this.iShiftX;
    }

    protected final int getShiftY() {
        return this.iShiftY;
    }

    protected final int getPointsSize() {
        return this.iPointsSize;
    }

    protected final int getNeighboringProvincesSize() {
        return this.iNeighboringProvincesSize;
    }

    protected final int getNeighboringSeaProvincesSize() {
        return this.iNeighboringSeaProvincesSize;
    }

    protected final void addNeighboringProvince(int nProvinceID) {
        this.lNeighboringProvinces.add((short)nProvinceID);
        this.iNeighboringProvincesSize = this.lNeighboringProvinces.size();
    }

    protected final void removeNeighboringProvince(int nProvinceID) {
        for(int i = 0; i < this.iNeighboringProvincesSize; ++i) {
            if (nProvinceID == this.getNeighboringProvinces(i)) {
                this.lNeighboringProvinces.remove(i);
                this.iNeighboringProvincesSize = this.lNeighboringProvinces.size();
                return;
            }
        }

    }

    protected final int getBasinID() {
        return this.iBasin;
    }

    protected final void setBasin(int iBasin) {
        this.iBasin = iBasin;
    }

    protected final void addNeighboringSeaProvince(int nProvinceID) {
        this.lNeighboringSeaProvinces.add((short)nProvinceID);
        this.iNeighboringSeaProvincesSize = this.lNeighboringSeaProvinces.size();
    }

    protected final void removeNeighboringSeaProvince(int nProvinceID) {
        for(int i = 0; i < this.iNeighboringSeaProvincesSize; ++i) {
            if (nProvinceID == this.getNeighboringSeaProvinces(i)) {
                this.lNeighboringSeaProvinces.remove(i);
                this.iNeighboringSeaProvincesSize = this.lNeighboringSeaProvinces.size();
                return;
            }
        }

    }

    protected final int getNeighboringProvinces(int i) {
        return (Short)this.lNeighboringProvinces.get(i);
    }

    protected final int getNeighboringSeaProvinces(int i) {
        return (Short)this.lNeighboringSeaProvinces.get(i);
    }

    protected final boolean getBelowZero() {
        return this.belowZeroPosX;
    }

    protected final List getProvinceBordersLandByLand() {
        return this.lProvinceBordersLandByLand;
    }

    protected final Province_Border getProvinceBordersLandByLand(int withProvinceID) {
        for(int i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                return (Province_Border)this.lProvinceBordersLandByLand.get(i);
            }
        }

        return new Province_Border(0, new ArrayList(), new ArrayList());
    }

    protected final List getProvinceBordersLandBySea() {
        return this.lProvinceBordersLandBySea;
    }

    protected final void addProvinceBorder(int withProvinceID, List nPointsX, List nPointsY) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                return;
            }
        }

        for(i = 0; i < this.iProvinceBordersLandBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandBySea.get(i)).getWithProvinceID()) {
                return;
            }
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).getWithProvinceID()) {
                return;
            }
        }

        if (this.lProvinceBordersLandByLand == null) {
            this.lProvinceBordersLandByLand = new ArrayList();
        }

        this.lProvinceBordersLandByLand.add(new Province_Border(withProvinceID, nPointsX, nPointsY));
        this.iProvinceBordersLandByLandSize = this.lProvinceBordersLandByLand.size();
        ((Province_Border)this.lProvinceBordersLandByLand.get(this.iProvinceBordersLandByLandSize - 1)).updateDrawProvinceBorder(this.getProvinceID());
    }

    protected final void removeProvinceBorder(int withProvinceID) {
        int i;
        for(i = 0; i < this.iProvinceBordersLandByLandSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandByLand.get(i)).getWithProvinceID()) {
                this.lProvinceBordersLandByLand.remove(i);
                this.iProvinceBordersLandByLandSize = this.lProvinceBordersLandByLand.size();
                return;
            }
        }

        for(i = 0; i < this.iProvinceBordersLandBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandBySea.get(i)).getWithProvinceID()) {
                this.lProvinceBordersLandBySea.remove(i);
                this.iProvinceBordersLandBySeaSize = this.lProvinceBordersLandBySea.size();
                return;
            }
        }

        for(i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).getWithProvinceID()) {
                this.lProvinceBordersSeaBySea.remove(i);
                this.iProvinceBordersSeaBySeaSize = this.lProvinceBordersSeaBySea.size();
                return;
            }
        }

    }

    protected final Province_Border getProvinceBordersLandBySea(int withProvinceID) {
        for(int i = 0; i < this.iProvinceBordersLandBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandBySea.get(i)).getWithProvinceID()) {
                return (Province_Border)this.lProvinceBordersLandBySea.get(i);
            }
        }

        Gdx.app.error("AoC - Province", "lProvinceBordersLandBySea: " + this.iProvinceID + " - " + withProvinceID);
        return new Province_Border(0, new ArrayList(), new ArrayList());
    }

    protected final int getProvinceBordersLandBySea_ID(int withProvinceID) {
        for(int i = 0; i < this.iProvinceBordersLandBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersLandBySea.get(i)).getWithProvinceID()) {
                return i;
            }
        }

        Gdx.app.error("AoC - Province", "getProvinceBordersLandBySea_ID: " + this.iProvinceID + " - " + withProvinceID);
        return -1;
    }

    protected final List getProvinceBordersSeaBySea() {
        return this.lProvinceBordersSeaBySea;
    }

    protected final Province_Border getProvinceBordersSeaBySea(int withProvinceID) {
        for(int i = 0; i < this.iProvinceBordersSeaBySeaSize; ++i) {
            if (withProvinceID == ((Province_Border)this.lProvinceBordersSeaBySea.get(i)).getWithProvinceID()) {
                return (Province_Border)this.lProvinceBordersSeaBySea.get(i);
            }
        }

        Gdx.app.error("AoC - Province", "getProvinceBordersSeaBySea: " + this.iProvinceID + " - " + withProvinceID);
        return new Province_Border(0, new ArrayList(), new ArrayList());
    }

    protected final int getTranslateProvincePosX() {
        return this.iTranslateProvincePosX;
    }

    protected final void setTranslateProvincePosX(int iTranslateProvincePosX) {
        this.iTranslateProvincePosX = iTranslateProvincePosX;
    }

    protected final boolean getDrawProvince() {
        return this.drawProvince;
    }

    protected final void setDrawProvince(boolean drawProvince) {
        this.drawProvince = drawProvince;
    }

    protected final int getCivsSize() {
        return this.saveProvinceData.iCivsSize;
    }

    protected final int getArmyWidth(int i) {
        return ((Province_Army)this.saveProvinceData.lArmies.get(i)).getArmyWidth();
    }

    protected final void updateArmyWidth(int i) {
        ((Province_Army)this.saveProvinceData.lArmies.get(i)).updateArmyWidth_Just(i);
    }

    protected final void updateArmyWidth_ALL() {
        try {
            if (CFG.menuManager.getInGameView()) {
                CFG.game.addLoadArmiesWidth_ErrorIDs(this.getProvinceID());
            }
        } catch (IndexOutOfBoundsException var2) {
        }

    }

    protected final Province_Army getArmy_Obj(int i) {
        return (Province_Army)this.saveProvinceData.lArmies.get(i);
    }

    protected final int getArmy(int i) {
        return ((Province_Army)this.saveProvinceData.lArmies.get(i)).getArmy();
    }

    protected final int getProvinceBordersLandByLandSize() {
        return this.iProvinceBordersLandByLandSize;
    }

    protected final int getProvinceBordersLandBySeaSize() {
        return this.iProvinceBordersLandBySeaSize;
    }

    protected final int getProvinceBordersSeaBySeaSize() {
        return this.iProvinceBordersSeaBySeaSize;
    }

    protected final void setShiftArmyX(int iShiftArmyX) {
        this.iShiftX = iShiftArmyX;
    }

    protected final void setShiftArmyY(int iShiftArmyY) {
        this.iShiftY = iShiftArmyY;
    }

    protected final boolean getSeaProvince() {
        return this.seaProvince;
    }

    protected final void setFromCivID(int iFromCivID) {
        this.iFromCivID = iFromCivID;
        this.updateColorTime = true;
    }

    protected final int getContinent() {
        return this.iContinentID;
    }

    protected final void setContinent(int iContinentID) {
        this.iContinentID = iContinentID;
    }

    protected final int getRegion() {
        return this.iRegionID;
    }

    protected final void setRegion(int iRegionID) {
        this.iRegionID = iRegionID;
    }

    protected final int getWasteland() {
        return this.saveProvinceData.wastelandLevel;
    }

    protected final void setWasteland(int wastelandLevel) {
        this.saveProvinceData.wastelandLevel = wastelandLevel;

        for(int i = 0; i < this.getNeighboringProvincesSize(); ++i) {
            if (this.getProvinceID() < this.getNeighboringProvinces(i)) {
                if (CFG.getMetProvince(this.getProvinceID()) && CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                    if (wastelandLevel >= 0) {
                        this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder_Just(CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() < 0, this.getNeighboringProvinces(i));
                    } else if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() >= 0) {
                        this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder_Just(true, this.getNeighboringProvinces(i));
                    } else {
                        this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder_Just(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID() != CFG.game.getProvince(this.getProvinceID()).getCivID(), this.getNeighboringProvinces(i));
                    }
                } else if (!CFG.getMetProvince(this.getProvinceID()) && !CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                    this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder_Just(false, this.getNeighboringProvinces(i));
                } else {
                    this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsCivilizationBorder_Just(true, this.getNeighboringProvinces(i));
                }

                if (CFG.getMetProvince(this.getProvinceID()) && CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                    this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsWastelandBorder(wastelandLevel >= 0 || CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() >= 0, this.getProvinceID());
                } else {
                    this.getProvinceBordersLandByLand(this.getNeighboringProvinces(i)).setIsWastelandBorder(false, this.getProvinceID());
                }
            } else {
                if (CFG.getMetProvince(this.getProvinceID()) && CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                    if (wastelandLevel >= 0) {
                        CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder_Just(CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() < 0, this.getNeighboringProvinces(i));
                    } else if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() >= 0) {
                        CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder_Just(true, this.getNeighboringProvinces(i));
                    } else {
                        CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder_Just(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID() != CFG.game.getProvince(this.getProvinceID()).getCivID(), this.getNeighboringProvinces(i));
                    }
                } else if (!CFG.getMetProvince(this.getProvinceID()) && !CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                    CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder_Just(false, this.getNeighboringProvinces(i));
                } else {
                    CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsCivilizationBorder_Just(true, this.getNeighboringProvinces(i));
                }

                if (CFG.getMetProvince(this.getProvinceID()) && CFG.getMetProvince(this.getNeighboringProvinces(i))) {
                    CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsWastelandBorder(wastelandLevel >= 0 || CFG.game.getProvince(this.getNeighboringProvinces(i)).getWasteland() >= 0, this.getProvinceID());
                } else {
                    CFG.game.getProvince(this.getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getProvinceID()).setIsWastelandBorder(false, this.getProvinceID());
                }
            }
        }

        CFG.game.setUpdateProvincesInView(true);
    }

    protected final String getName() {
        return this.sProvinceName.length() == 0 && this.getCitiesSize() > 0 ? this.getCity(0).getCityName() : this.sProvinceName;
    }

    protected final void setName(String sProvinceName) {
        this.sProvinceName = sProvinceName;
    }

    protected final void setLevelOfPort(int iPort) {
        this.saveProvinceData.iPort = iPort;
        if (iPort > 0) {
            this.setDrawCities(true);
            if (this.getCivRegionID() >= 0 && this.getCivID() > 0) {
                try {
                    CFG.game.getCiv(this.getCivID()).getCivRegion(this.getCivRegionID()).setSeaAccess_HavePort(true);
                } catch (IndexOutOfBoundsException var3) {
                }
            }
        }

    }

    protected final int getLevelOfFarm() {
        return this.saveProvinceData.iFarm;
    }

    protected final void setLevelOfFarm(int iFarm) {
        this.saveProvinceData.iFarm = iFarm;
    }

    protected final int getLevelOfWorkshop() {
        return this.saveProvinceData.iWorkshop;
    }

    protected final void setLevelOfWorkshop(int iWorkshop) {
        this.saveProvinceData.iWorkshop = iWorkshop;
    }

    protected final int getLevelOfLibrary() {
        return this.saveProvinceData.iLibrary;
    }

    protected final void setLevelOfLibrary(int iLibrary) {
        this.saveProvinceData.iLibrary = iLibrary;
    }

    protected final int getLevelOfArmoury() {
        return this.saveProvinceData.iArmoury;
    }

    protected final void setLevelOfArmoury(int iArmoury) {
        this.saveProvinceData.iArmoury = iArmoury;
        if (this.saveProvinceData.iArmoury > 0) {
            this.setDrawCities(true);
        }

    }

    protected final int getLevelOfSupply() {
        return this.saveProvinceData.iSupply;
    }

    protected final void setLevelOfSupply(int iSupply) {
        this.saveProvinceData.iSupply = iSupply;
    }

    protected final int getPortShiftX() {
        try {
            return this.provincePort.getShiftX();
        } catch (NullPointerException var2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }

            return 0;
        }
    }

    protected final int getPortShiftY() {
        try {
            return this.provincePort.getShiftY();
        } catch (NullPointerException var2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }

            return 0;
        }
    }

    protected final int getLevelOfPort() {
        return this.saveProvinceData.iPort;
    }

    protected final void setLevelOfFort(int iFort) {
        this.saveProvinceData.iFort = iFort;
    }

    protected final void setLevelOfWatchTower(int iWatchTower) {
        this.saveProvinceData.iWatchTower = iWatchTower;
    }

    protected final int getLevelOfWatchTower() {
        return this.saveProvinceData.iWatchTower;
    }

    protected final int getLevelOfFort() {
        return this.saveProvinceData.iFort;
    }

    protected final boolean getIsCapital() {
        return this.saveProvinceData.isCapital && CFG.game.getCiv(this.getCivID()).getCapitalProvinceID() == this.getProvinceID();
    }

    protected final boolean getIsCapital_Just() {
        return this.saveProvinceData.isCapital;
    }

    protected final void setIsCapital(boolean isCapital) {
        this.saveProvinceData.isCapital = isCapital;
    }

    protected final void removeCapitalCityIcon() {
        for(int i = 0; i < this.getCitiesSize(); ++i) {
            if (this.getCity(i).getCityLevel() == CFG.getEditorCityLevel(0)) {
                this.getCity(i).setCityLevel(CFG.getEditorCityLevel(1));
            }
        }

    }

    protected final Province_Population getPopulationData() {
        return this.saveProvinceData.oPopulation;
    }

    protected final void setPopulationData(Province_Population nProvince_Population) {
        this.saveProvinceData.oPopulation = nProvince_Population;
    }

    protected final void buildProvinceCore() {
        this.saveProvinceData.oProvinceCore = null;
        this.saveProvinceData.oProvinceCore = new Province_Core();
        if (this.getCivID() > 0) {
            this.saveProvinceData.oProvinceCore.addNewCore(this.getCivID(), Game_Calendar.TURN_ID);
            this.saveProvinceData.oProvinceCore.increaseOwnership(this.getCivID(), this.getProvinceID());
        }

    }

    protected final void resetCore() {
        this.saveProvinceData.oProvinceCore = null;
        this.saveProvinceData.oProvinceCore = new Province_Core();
    }

    protected final Province_Core getCore() {
        return this.saveProvinceData.oProvinceCore;
    }

    protected final void updateProvincePopulationLosses(int nLosses, float fLossesModifier) {
        nLosses = (int)Math.ceil((double)((float)nLosses * fLossesModifier));
        int nStartPopulation = this.getPopulationData().getPopulation();
        if (this.getPopulationData().getPopulation() > 92) {
            for(int i = 0; i < this.getPopulationData().getNationalitiesSize(); ++i) {
                this.getPopulationData().setPopulationOfCivID(this.getPopulationData().getCivID(i), (int)Math.ceil((double)((float)this.getPopulationData().getPopulationID(i) - (float)this.getPopulationData().getPopulationID(i) / (float)nStartPopulation * (float)nLosses)));
            }
        }

    }

    protected final void updateProvinceEconomyLosses(int nLosses, float fLossesModifier) {
        nLosses = (int)Math.ceil((double)((float)nLosses * fLossesModifier));
        this.setEconomy(this.getEconomy() - nLosses);
    }

    protected final int getEconomy() {
        return this.saveProvinceData.iEconomy;
    }

    protected final void setEconomy(int iEconomy) {
        this.saveProvinceData.iEconomy = iEconomy;
        if (this.saveProvinceData.iEconomy < 99) {
            this.saveProvinceData.iEconomy = 99;
        }

    }

    protected final int getProvinceID() {
        return this.iProvinceID;
    }

    protected final void initProvinceArmyBoxes() {
        if (this.lProvince_ArmyBoxes == null) {
            this.lProvince_ArmyBoxes = new ArrayList();
        }

    }

    protected final List getProvinceArmyBoxes() {
        return this.lProvince_ArmyBoxes;
    }

    protected final void setProvinceArmyBoxes(List nSet) {
        this.lProvince_ArmyBoxes = nSet;
    }

    protected final boolean getIsSupplied() {
        try {
            return CFG.game.getCiv(this.getCivID()).getCivRegion(this.getCivRegionID()).getIsSupplied();
        } catch (IndexOutOfBoundsException var2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }

            return true;
        } catch (NullPointerException var3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var3);
            }

            return true;
        }
    }

    protected final int getCivRegionID() {
        return this.iCivRegionID;
    }

    protected final void setCivRegionID(int iCivRegionID) {
        this.iCivRegionID = iCivRegionID;
    }

    protected final void setTerrainTypeID(int iTerrainTypeID) {
        this.iTerrainTypeID = iTerrainTypeID;
    }

    protected final int getTerrainTypeID() {
        return this.iTerrainTypeID;
    }

    protected final float getGrowthRate_Population() {
        return this.fPopulationGrowthRate;
    }

    protected final float getGrowthRate_Population_WithFarm() {
        return this.fPopulationGrowthRate + BuildingsManager.getFarm_GrowthRateBonus(this.getLevelOfFarm());
    }

    protected final float getGrowthRate_NewColony() {
        return (float)this.saveProvinceData.iNewColonyBonus * (0.005F + 0.0125F * this.getGrowthRate_Population());
    }

    protected final float getGrowthRate_Population_WithFarm_WithTerrain() {
        return Math.max(this.fPopulationGrowthRate + BuildingsManager.getFarm_GrowthRateBonus(this.getLevelOfFarm()) + CFG.terrainTypesManager.getPopulationGrowth(this.getTerrainTypeID()) + this.getGrowthRate_NewColony(), 0.02F);
    }

    protected final void setGrowthRate_Population(float fPopulationGrowthRate) {
        this.fPopulationGrowthRate = fPopulationGrowthRate;
    }

    protected final float getDevelopmentLevel() {
        return this.saveProvinceData.fDevelopmentLevel;
    }

    protected final void setDevelopmentLevel(float nDevelopmentLevel) {
        this.saveProvinceData.fDevelopmentLevel = nDevelopmentLevel;
        if (this.saveProvinceData.fDevelopmentLevel > CFG.game.getCiv(this.getCivID()).getTechnologyLevel()) {
            this.saveProvinceData.fDevelopmentLevel = CFG.game.getCiv(this.getCivID()).getTechnologyLevel();
        } else if (this.saveProvinceData.fDevelopmentLevel < 0.01F) {
            this.saveProvinceData.fDevelopmentLevel = 0.01F;
        }

    }

    protected final float getHappiness() {
        return this.saveProvinceData.fHappiness;
    }

    protected final void setHappiness(float nHappiness) {
        this.saveProvinceData.fHappiness = nHappiness;
        if (this.saveProvinceData.fHappiness > 1.0F) {
            this.saveProvinceData.fHappiness = 1.0F;
        } else if (this.saveProvinceData.fHappiness < 0.0F) {
            this.saveProvinceData.fHappiness = 0.0F;
        }

    }

    protected final float getRevolutionaryRisk() {
        return this.saveProvinceData.fRevolutionaryRisk;
    }

    protected final void setRevolutionaryRisk(float nRevolutionaryRisk) {
        if (nRevolutionaryRisk > 0.99F) {
            this.saveProvinceData.fRevolutionaryRisk = 0.99F;
        } else if (nRevolutionaryRisk < 0.0F) {
            this.saveProvinceData.fRevolutionaryRisk = 0.0F;
        } else {
            this.saveProvinceData.fRevolutionaryRisk = nRevolutionaryRisk;
        }

    }

    protected final int getPotential() {
        return this.iPotential;
    }

    protected final int getPotentialRegion() {
        try {
            return CFG.game.getCiv(this.getCivID()).getCivRegion(this.getCivRegionID()).iAveragePotential;
        } catch (IndexOutOfBoundsException var2) {
            return this.getPotential();
        } catch (NullPointerException var3) {
            return this.getPotential();
        }
    }

    protected final int getRegion_NumOfProvinces() {
        try {
            return CFG.game.getCiv(this.getCivID()).getCivRegion(this.getCivRegionID()).getProvincesSize();
        } catch (IndexOutOfBoundsException var2) {
            return 1;
        } catch (NullPointerException var3) {
            return 1;
        }
    }

    protected final int getPotentialModified(int nCivID) {
        return (int)((float)this.iPotential * (this.getTrueOwnerOfProvince() == nCivID ? CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_POTENTIAL_MODIFIED_OWN_LOST_PROVINCE : 1.0F));
    }

    protected final int getPotentialModified_WAR_MoveFrom(int nCivID) {
        int nProvinces = 0;
        int nPotential = 0;
        boolean rebels = false;

        try {
            for(int i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID())) {
                    if (CFG.game.getCiv(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID()).getCivRegion(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivRegionID()).getProvincesSize() < CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_CLOSE_REGION_PROVINCES) {
                        nPotential = (int)((float)nPotential + (float)CFG.game.getProvince(this.getNeighboringProvinces(i)).getPotential() * CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_CLOSE_REGION_EXTRA_SCORE * (CFG.game.getProvince(this.getNeighboringProvinces(i)).getTrueOwnerOfProvince() == nCivID ? CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_POTENTIAL_MODIFIED_OWN_LOST_PROVINCE : 1.0F));
                        ++nProvinces;
                    }

                    if (CFG.game.getProvince(this.getNeighboringProvinces(i)).isOccupied() && CFG.game.getCiv(CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID()).getIdeologyID() == CFG.ideologiesManager.REBELS_ID) {
                        rebels = true;
                    }
                }
            }
        } catch (NullPointerException var6) {
            return this.iPotential * (rebels ? 10 : 1);
        } catch (IndexOutOfBoundsException var7) {
            return this.iPotential * (rebels ? 10 : 1);
        }

        return nProvinces > 0 ? (this.iPotential + nPotential / nProvinces) * (rebels ? 10 : 1) : this.iPotential * (rebels ? 10 : 1);
    }

    protected final int getPotentialModified_WAR_MoveTo(int nCivID) {
        try {
            if (CFG.game.getCiv(this.getCivID()).getCivRegion(this.getCivRegionID()).getProvincesSize() < CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_CLOSE_REGION_PROVINCES) {
                return (int)((float)this.getPotential() * CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_CLOSE_REGION_EXTRA_SCORE * (float)(CFG.game.getCiv(this.getCivID()).getIdeologyID() == CFG.ideologiesManager.REBELS_ID ? 10 : 1));
            } else {
                int ownProvinces = 0;
                int enemyProvinces = 0;

                for(int i = 0; i < this.getNeighboringProvincesSize(); ++i) {
                    if (CFG.game.isAlly(nCivID, CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID())) {
                        ++ownProvinces;
                    } else if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID())) {
                        ++enemyProvinces;
                    }
                }

                if (enemyProvinces > 1 && ownProvinces == 1) {
                    return (int)((float)this.iPotential * 0.25F) * (CFG.game.getCiv(this.getCivID()).getIdeologyID() == CFG.ideologiesManager.REBELS_ID ? 10 : 1);
                } else {
                    return this.iPotential * (CFG.game.getCiv(this.getCivID()).getIdeologyID() == CFG.ideologiesManager.REBELS_ID ? 10 : 1);
                }
            }
        } catch (NullPointerException var5) {
            return this.iPotential * (CFG.game.getCiv(this.getCivID()).getIdeologyID() == CFG.ideologiesManager.REBELS_ID ? 10 : 1);
        } catch (IndexOutOfBoundsException var6) {
            return this.iPotential * (CFG.game.getCiv(this.getCivID()).getIdeologyID() == CFG.ideologiesManager.REBELS_ID ? 10 : 1);
        }
    }

    protected final void setPotential(int iPotential) {
        this.iPotential = iPotential;
    }

    protected final void addPotential(int iPotential) {
        this.iPotential += iPotential;
    }

    protected final int getDangerLevel() {
        return this.iDangerLevel;
    }

    protected final void setDangerLevel(int iDangerLevel) {
        this.iDangerLevel = iDangerLevel;
    }

    protected final int getDangerLevel_WithArmy() {
        return this.iDangerLevel_WithArmy;
    }

    protected final void setDangerLevel_WithArmy(int iDangerLevel_WithArmy) {
        this.iDangerLevel_WithArmy = iDangerLevel_WithArmy;
    }

    protected final void addDangerLevel(int iDangerLevel) {
        this.iDangerLevel += iDangerLevel;
    }

    protected final int getRecruitableArmyPoints() {
        return this.iRecruitableArmyPoints;
    }

    protected final void buildRecruitableArmyPoints() {
        if (this.getCivID() != 0 && !this.isOccupied()) {
            this.iRecruitableArmyPoints = CFG.gameAction.getRecruitableArmy(this.getProvinceID(), this.getCivID());
            if (this.getLevelOfArmoury() > 0) {
                this.iRecruitableArmyPoints = (int)((float)this.iRecruitableArmyPoints * 1.25F);
            }
        } else {
            this.iRecruitableArmyPoints = 0;
        }

    }

    protected final int getArmyWasRecruited() {
        return this.iArmyWasRecruited;
    }

    protected final void setArmyWasRecruited(int iArmyWasRecruited) {
        this.iArmyWasRecruited = (byte)iArmyWasRecruited;
    }

    protected final int getNumOfNeighboringNeutralProvinces() {
        return this.iNumOfNeighboringNeutralProvinces;
    }

    protected final void setNumOfNeighboringNeutralProvinces(int iNumOfNeighboringNeutralProvinces) {
        this.iNumOfNeighboringNeutralProvinces = (byte)iNumOfNeighboringNeutralProvinces;
    }

    protected final void incNumOfNeighboringNeutralProvinces() {
        ++this.iNumOfNeighboringNeutralProvinces;
    }

    protected final boolean getBordersWithEnemy() {
        return this.bordersWithEnemy;
    }

    protected final void setBordersWithEnemy(boolean bordersWithEnemy) {
        this.bordersWithEnemy = bordersWithEnemy;
    }

    protected final int getWasAttacked() {
        return this.saveProvinceData.wasAttacked;
    }

    protected final void setWasAttacked(int wasAttacked) {
        this.saveProvinceData.wasAttacked = (byte)Math.max(0, wasAttacked);
    }

    protected final int getWasConquered() {
        return this.saveProvinceData.wasConquered;
    }

    protected final void setWasConquered(byte wasConquered) {
        if (wasConquered < 0) {
            this.saveProvinceData.wasConquered = 0;
        } else {
            this.saveProvinceData.wasConquered = wasConquered;
        }

    }

    protected final int getNeighbooringProvinceOfCivWasLost() {
        return this.saveProvinceData.neighbooringProvinceOfCivWasLost;
    }

    protected final void setNeighbooringProvinceOfCivWasLost(byte neighbooringProvinceOfCivWasLost) {
        if (neighbooringProvinceOfCivWasLost < 0) {
            this.saveProvinceData.neighbooringProvinceOfCivWasLost = 0;
        } else {
            this.saveProvinceData.neighbooringProvinceOfCivWasLost = neighbooringProvinceOfCivWasLost;
        }

    }

    protected final boolean getIsPartOfHolyRomanEmpire() {
        return this.saveProvinceData.isPartOfHolyRomaEmpire;
    }

    protected final void setIsPartOfHolyRomanEmpire(boolean isPartOfHolyRomaEmpire) {
        this.saveProvinceData.isPartOfHolyRomaEmpire = isPartOfHolyRomaEmpire;
    }

    protected final float getProvinceStability() {
        return this.fProvinceStability;
    }

    protected final void updateProvinceStability() {
        if (this.getCivID() == 0) {
            this.fProvinceStability = 1.0F;
        } else {
            this.fProvinceStability = 0.0F;
            this.fProvinceStability += this.updateStability_Score_Population();
            this.fProvinceStability -= this.updateStability_Score_RevRisk();
            this.fProvinceStability += this.updateStability_Score_Core();
            this.fProvinceStability += this.updateStability_Score_Occupied();
            //decrease stability based on perms as vassal (if applicable)
            this.fProvinceStability *= this.updateStability_Score_Puppet(); //this is fucked up need to fix
            if (this.fProvinceStability < 1.0F) {
                this.fProvinceStability += this.updateStability_Score_Army();
            }

            this.fProvinceStability = Math.min(this.fProvinceStability, 1.0F);
            this.fProvinceStability -= this.updateStability_Score_Disease();
            this.fProvinceStability = Math.max(this.fProvinceStability, 0.01F);
            if (!this.isOccupied() && CFG.game.getCiv(this.getCivID()).civGameData.civPersonality.MIN_PROVINCE_STABILITY > this.fProvinceStability && CFG.game.getCiv(this.getCivID()).civGameData.civPersonality.MIN_HAPPINESS_TO_ASSMILIATE_PROVINCE < this.getHappiness()) {
                CFG.game.getCiv(this.getCivID()).lProvincesWithLowStability.add(this.getProvinceID());
            }

        }
    }

    protected final float updateStability_Score_Core() {
        return this.getCore().getHaveACore(this.getCivID()) ? 0.05F : 0.0F;
    }

    protected final float updateStability_Score_Occupied() {
        return this.isOccupied() ? 0.45F * (0.85F + 0.2F * CFG.game.getCiv(this.getCivID()).getTechnologyLevel()) : 0.0F;
    }

    protected final float updateStability_Score_Puppet() {
        if (!CFG.game.getCiv(this.getCivID()).getIsPupet()) return 1.0F;
        int iAutonomyStatus = CFG.game.getCiv(CFG.game.getCiv(this.getCivID()).getPuppetOfCivID()).getVassal_AutonomyStatus(this.getCivID()).getIndexOf();
        float iStab = 1.0F;

        if (!CFG.gameAutonomy.lAutonomy.get(iAutonomyStatus).isMilitaryControl()) {
            //iStab /= (CFG.gameAction.buildRank_Score_Prestige(this.getCivID())/5F);
            iStab *= 0.9F;
        } else {
            iStab *= 1.025F;
        }
        if (!CFG.gameAutonomy.lAutonomy.get(iAutonomyStatus).isEconomicControl()) {
            //iStab /= (CFG.gameAction.buildRank_Score_Economy(this.getCivID())/5F);
            iStab *= 0.9F;
        } else {
            iStab *= 1.025F;
        }
        if (!CFG.gameAutonomy.lAutonomy.get(iAutonomyStatus).isTradeIntegration()) {
            iStab *= 0.95F;
        }
        if (CFG.gameAutonomy.lAutonomy.get(iAutonomyStatus).isAutoJoinWarPerms()) {
            iStab *= 0.95F;
        }

        return iStab;
    }

    protected final float updateStability_Score_Disease() {
        return this.saveProvinceData.provincePlague != null ? 0.2F : 0.0F;
    }

    protected final float updateStability_Score_RevRisk() {
        return 0.2F * this.getRevolutionaryRisk() + (this.saveProvinceData.iSupportRebelsSize > 0 ? 0.05F : 0.0F);
    }

    protected final float updateStability_Score_Happiness() {
        if (this.getHappiness() < 0.5F) {
            return this.isOccupied() ? 0.0F : -0.55F * (0.5F - this.getHappiness());
        } else {
            return this.isOccupied() ? 0.3F * this.getHappiness() / 2.0F : 0.3F * this.getHappiness();
        }
    }

    protected final float updateStability_Score_Army() {
        int tempNeighbooringArmy = 0;

        for(int i = 0; i < this.getNeighboringProvincesSize(); ++i) {
            if (CFG.game.getProvince(this.getNeighboringProvinces(i)).getCivID() == this.getCivID()) {
                tempNeighbooringArmy += CFG.game.getProvinceArmy(this.getNeighboringProvinces(i));
            }
        }

        return 0.65F * Math.min(((float)CFG.game.getProvinceArmy(this.getProvinceID()) + (float)tempNeighbooringArmy * 0.185F) / ((float)this.getPopulationData().getPopulation() / 15.97254F), 1.0F);
    }

    protected final float updateStability_Score_Population() {
        float out = 0.0F;
        int nOurPeople = 0;
        int largestGroup = 0;
        int totalPop = 0;

        for(int j = 0; j < this.getPopulationData().getNationalitiesSize(); ++j) {
            if (this.getPopulationData().getCivID(j) == this.getCivID()) {
                nOurPeople += this.getPopulationData().getPopulationID(j);
            } else if (this.getPopulationData().getCivID(j) == 0) {
                nOurPeople += (int)((float)this.getPopulationData().getPopulationID(j) * 0.75F);
            } else if (largestGroup < this.getPopulationData().getPopulationID(j)) {
                largestGroup = this.getPopulationData().getPopulationID(j);
            }

            totalPop += this.getPopulationData().getPopulationID(j);
        }

        if (nOurPeople < largestGroup) {
            out += 0.215F * ((float)nOurPeople / (float)largestGroup);
        } else {
            out += 0.215F * ((float)largestGroup / (float)nOurPeople);
        }

        out += 1.275F * ((float)nOurPeople / (float)totalPop) * (0.725F + 0.275F * this.getHappiness());
        return out;
    }

    protected final int getIsNotSuppliedForXTurns() {
        return this.saveProvinceData.isNotSuppliedForXTurns;
    }

    protected final void setIsNotSuppliedForXTurns(int isNotSuppliedForXTurns) {
        this.saveProvinceData.isNotSuppliedForXTurns = isNotSuppliedForXTurns;
    }

    protected final void updateIsNotSuppliedForXTurns() {
        if (!this.getIsSupplied() && !CFG.ideologiesManager.getIdeology(CFG.game.getCiv(this.getCivID()).getIdeologyID()).REVOLUTIONARY) {
            ++this.saveProvinceData.isNotSuppliedForXTurns;
            CFG.game.getCiv(this.getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_ProvincesNotSupplied(this.getCivID(), this.getProvinceID()));
            if (this.saveProvinceData.isNotSuppliedForXTurns > 2) {
                for(int i = this.getCivsSize() - 1; i >= 0; --i) {
                    if (this.getArmy(i) > 0) {
                        int armyStrave = (int)Math.max(Math.ceil((double)((float)this.getArmy(i) * 0.04F * (float)(this.saveProvinceData.isNotSuppliedForXTurns - 2))), 10.0);
                        armyStrave = Math.min(armyStrave, this.getArmy(i));
                        if (armyStrave > 0) {
                            CFG.game.getCiv(this.getCivID(i)).setNumOfUnits(CFG.game.getCiv(this.getCivID(i)).getNumOfUnits() - armyStrave);
                            this.updateArmy(this.getCivID(i), this.getArmy(i) - armyStrave);
                            CFG.game.getCiv(this.getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_ProvincesNotSupplied_Straves(this.getCivID(), this.getProvinceID(), armyStrave));
                        }
                    }
                }
            }

            if (this.saveProvinceData.isNotSuppliedForXTurns >= 10 && this.isOccupied() && CFG.game.getProvinceArmy(this.getProvinceID()) <= 0) {
                CFG.game.getCiv(this.getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_ProvincesNotSupplied_LostControl(this.getCivID(), this.getProvinceID()));
                CFG.game.getCiv(this.getTrueOwnerOfProvince()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_ProvincesNotSupplied_LostControl_EnemyLost(this.getCivID(), this.getProvinceID()));
                this.resetArmies(0);
                this.setCivID(this.getTrueOwnerOfProvince(), false, true);
                this.saveProvinceData.isNotSuppliedForXTurns = -1;
            }
        } else {
            this.saveProvinceData.isNotSuppliedForXTurns = -1;
        }

    }

    protected final void updateDefensivePosition() {
        if (!this.isOccupied() && this.getIsSupplied()) {
            if (this.getArmy(0) > 0) {
                ++this.saveProvinceData.iDefensivePosition;
                if (this.saveProvinceData.iDefensivePosition > 50) {
                    this.saveProvinceData.iDefensivePosition = 50;
                }
            } else {
                this.saveProvinceData.iDefensivePosition = 0;
            }
        } else {
            this.saveProvinceData.iDefensivePosition = 0;
        }

    }

    protected final int getDefensivePosition() {
        return Math.max(this.saveProvinceData.iDefensivePosition, 0);
    }

    protected final void updateNewColony() {
        if (this.saveProvinceData.iNewColonyBonus > 0) {
            --this.saveProvinceData.iNewColonyBonus;
        }

    }

    protected final boolean civSupportsRebels(int nCivID) {
        for(int i = this.saveProvinceData.iSupportRebelsSize - 1; i >= 0; --i) {
            if (((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iByCivID == nCivID) {
                return true;
            }
        }

        return false;
    }

    protected final Province_SupportRebels_Help addSupportRebels(Province_SupportRebels nData) {
        for(int i = this.saveProvinceData.iSupportRebelsSize - 1; i >= 0; --i) {
            if (((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iByCivID == nData.iByCivID && ((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID == nData.iRebelsCivID) {
                if (((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iTurnsLeft + nData.iTurnsLeft > 35) {
                    int out = 35 - ((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iTurnsLeft;
                    ((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iTurnsLeft = 35;
                    return new Province_SupportRebels_Help(out, true);
                }

                Province_SupportRebels var10000 = (Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i);
                var10000.iTurnsLeft += nData.iTurnsLeft;
                return new Province_SupportRebels_Help(nData.iTurnsLeft, true);
            }
        }

        this.saveProvinceData.lSupportRebels.add(nData);
        this.saveProvinceData.iSupportRebelsSize = this.saveProvinceData.lSupportRebels.size();
        return new Province_SupportRebels_Help(nData.iTurnsLeft, nData.iTurnsLeft >= 35);
    }

    protected final void runSupportRebels() {
        List runCivs = new ArrayList();

        for(int i = this.saveProvinceData.iSupportRebelsSize - 1; i >= 0; --i) {
            if (CFG.game.getCiv(((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID).getNumOfProvinces() > 0) {
                this.saveProvinceData.lSupportRebels.remove(i);
                this.saveProvinceData.iSupportRebelsSize = this.saveProvinceData.lSupportRebels.size();
            } else {
                --((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iTurnsLeft;
                boolean alreadyDone = false;

                int iNumOfSupporters;
                for(iNumOfSupporters = runCivs.size() - 1; iNumOfSupporters >= 0; --iNumOfSupporters) {
                    if (((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID == (Integer)runCivs.get(iNumOfSupporters)) {
                        alreadyDone = true;
                        break;
                    }
                }

                if (!alreadyDone) {
                    runCivs.add(((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID);
                    iNumOfSupporters = 0;

                    int popToAssimilate;
                    for(popToAssimilate = this.saveProvinceData.iSupportRebelsSize - 1; popToAssimilate >= 0; --popToAssimilate) {
                        if (((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(popToAssimilate)).iRebelsCivID == ((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID) {
                            ++iNumOfSupporters;
                        }
                    }

                    popToAssimilate = 0;
                    int ownerPop = 1 + this.getPopulationData().getPopulationOfCivID(((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID);

                    int assimilatedPop;
                    for(assimilatedPop = 0; assimilatedPop < this.getPopulationData().getNationalitiesSize(); ++assimilatedPop) {
                        if (this.getPopulationData().getCivID(assimilatedPop) != ((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID) {
                            popToAssimilate += this.getPopulationData().getPopulationID(assimilatedPop);
                        }
                    }

                    assimilatedPop = 0;
                    int j = this.getPopulationData().getNationalitiesSize() - 1;

                    for(int tCurrentPopChange = 0; j >= 0; --j) {
                        if (this.getPopulationData().getCivID(j) != ((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID) {
                            float tPerc = (0.00425F + (0.04971F + (float)CFG.oR.nextInt(1087) / 10000.0F) * ((float)ownerPop / (float)(popToAssimilate + ownerPop)) * this.getHappiness() * Math.min(1.0F - this.getDevelopmentLevel() / 3.75F, 1.0F)) * (1.0F + 0.025F * this.getRevolutionaryRisk()) * 0.8F;
                            tPerc *= DiplomacyManager.getSUPPORT_REBELS_ASSIMILATE_PERC(iNumOfSupporters);
                            tCurrentPopChange = (int)((float)this.getPopulationData().getPopulationID(j) * tPerc);
                            if (tCurrentPopChange == 0) {
                                tCurrentPopChange = CFG.oR.nextInt(2);
                            }

                            assimilatedPop += tCurrentPopChange;
                            this.getPopulationData().setPopulationOfCivID(this.getPopulationData().getCivID(j), this.getPopulationData().getPopulationID(j) - tCurrentPopChange);
                        }
                    }

                    this.getPopulationData().setPopulationOfCivID(((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID, this.getPopulationData().getPopulationOfCivID(((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iRebelsCivID) + assimilatedPop);
                }

                if (((Province_SupportRebels)this.saveProvinceData.lSupportRebels.get(i)).iTurnsLeft <= 0) {
                    this.saveProvinceData.lSupportRebels.remove(i);
                    this.saveProvinceData.iSupportRebelsSize = this.saveProvinceData.lSupportRebels.size();
                }
            }
        }

    }
}
