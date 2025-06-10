package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.*;

class CreateVassal_Data
{
    protected int iCapitalProvinceID;
    protected String sCivTag;
    protected Color oColor;
    protected boolean playAsVassal;
    protected int iAutonomyStatus;
    private Image flagOfCivilization;
    private Image flagOfCivilizationH;
    
    CreateVassal_Data() {
        super();
        this.iCapitalProvinceID = -1;
        this.sCivTag = null;
        this.oColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.playAsVassal = false;
        this.flagOfCivilization = null;
        this.flagOfCivilizationH = null;
        this.iAutonomyStatus = 0;
    }
    
    protected final void setCivTag(final String nTag) {
        this.sCivTag = nTag;
        this.loadFlag();
    }

    protected final void setCivAutonomyStatus(final int iAutonomyStatus) {
        this.iAutonomyStatus = iAutonomyStatus;
    }
    
    protected final void loadFlag() {
        this.dispose();
        if (this.sCivTag == null) {
            return;
        }
        try {
            Label_0391: {
                try {
                    this.flagOfCivilizationH = new Image(new Texture(Gdx.files.internal("game/flagsH/" + this.sCivTag + ".png")), Texture.TextureFilter.Linear);
                }
                catch (final GdxRuntimeException e) {
                    try {
                        this.flagOfCivilizationH = new Image(new Texture(Gdx.files.internal("game/flagsH/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + ".png")), Texture.TextureFilter.Linear);
                    }
                    catch (final GdxRuntimeException exr) {
                        if (CFG.isAndroid()) {
                            try {
                                this.flagOfCivilizationH = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "_FLH.png")), Texture.TextureFilter.Linear);
                            }
                            catch (final GdxRuntimeException eq) {
                                this.flagOfCivilizationH = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "_FLH.png")), Texture.TextureFilter.Linear);
                            }
                            break Label_0391;
                        }
                        this.flagOfCivilizationH = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "_FLH.png")), Texture.TextureFilter.Linear);
                    }
                }
            }
        }
        catch (final GdxRuntimeException | OutOfMemoryError ex) {
            this.dispose();
        }
        try {
            try {
                this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/flags/" + this.sCivTag + ".png")), Texture.TextureFilter.Linear);
            }
            catch (final GdxRuntimeException e) {
                try {
                    this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + ".png")), Texture.TextureFilter.Linear);
                }
                catch (final GdxRuntimeException exr) {
                    if (CFG.isAndroid()) {
                        try {
                            this.flagOfCivilization = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "_FL.png")), Texture.TextureFilter.Linear);
                        }
                        catch (final GdxRuntimeException eq) {
                            this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "_FL.png")), Texture.TextureFilter.Linear);
                        }
                        return;
                    }
                    this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.sCivTag) + "_FL.png")), Texture.TextureFilter.Linear);
                }
            }
        }
        catch (final GdxRuntimeException | OutOfMemoryError ex) {
            this.dispose();
        }
    }
    
    protected final Image getFlagOfCiv() {
        return this.flagOfCivilization;
    }
    
    protected final Image getFlagOfCivH() {
        if (this.flagOfCivilizationH == null) {
            return this.flagOfCivilization;
        }
        return this.flagOfCivilizationH;
    }
    
    protected final void dispose() {
        if (this.flagOfCivilizationH != null) {
            this.flagOfCivilizationH.getTexture().dispose();
            this.flagOfCivilizationH = null;
        }
        if (this.flagOfCivilization != null) {
            this.flagOfCivilization.getTexture().dispose();
            this.flagOfCivilization = null;
        }
    }
}
