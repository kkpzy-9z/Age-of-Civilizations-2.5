package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.*;
import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import java.io.*;
import com.badlogic.gdx.files.*;

class Menu_Decisions_Editor_Edit extends SliderMenu
{
    protected Menu_Decisions_Editor_Edit() {
        super();
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_Game((String)null, -1, CFG.PADDING, CFG.PADDING, true));
        menuElements.add(new Button_Game((String)null, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH - CFG.PADDING, CFG.PADDING, true));
        menuElements.add(new Minimap(CFG.GAME_WIDTH - CFG.map.getMapBG().getMinimapWidth(), CFG.GAME_HEIGHT - CFG.map.getMapBG().getMinimapHeight()));
        menuElements.add(new Text((String)null, -1, CFG.BUTTON_WIDTH + CFG.PADDING * 2, 0, CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.PADDING * 2) * 2, CFG.BUTTON_HEIGHT + CFG.PADDING * 2) {
            protected Color getColor(boolean isActive) {
                return isActive ? new Color(0.56F, 0.56F, 0.56F, 1.0F) : (this.getClickable() ? (this.getIsHovered() ? new Color(0.68F, 0.68F, 0.68F, 1.0F) : new Color(CFG.COLOR_TEXT_CNG_TOP_SCENARIO_NAME.r, CFG.COLOR_TEXT_CNG_TOP_SCENARIO_NAME.g, CFG.COLOR_TEXT_CNG_TOP_SCENARIO_NAME.b, 0.95F)) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
            }
        });
        this.initMenu((SliderMenuTitle)null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
        this.updateLanguage();
    }
    
    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Back"));
        this.getMenuElement(1).setText(CFG.langManager.get("Save"));
        this.getMenuElement(3).setText(CFG.langManager.get("AddNewDecision"));
    }
    
    @Override
    protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        CFG.drawEditorTitle_Edge_R_Reflected(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2);
        super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }
    
    @Override
    protected final void actionElement(final int iID) {
        switch (iID) {
            case 0: {
                this.onBackPressed();
                CFG.brushTool = false;
                CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR);
                CFG.menuManager.setBackAnimation(true);
                return;
            }
            case 1: {
                CFG.menuManager.saveDecisions_Edit_Data();
                if (CFG.civDecision_GameData.getName().isEmpty()) {
                    CFG.toast.setInView("-- " + CFG.langManager.get("Name") + " --", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                    CFG.toast.setTimeInView(6000);
                    break;
                }
                if (CFG.civDecision_GameData.getCivsSize() == 0) {
                    CFG.toast.setInView("-- " + CFG.langManager.get("Civilizations") + " --", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                    CFG.toast.setTimeInView(6000);
                    break;
                }
                this.saveDecision();
                this.onBackPressed();
                CFG.brushTool = false;
                CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR);
                CFG.menuManager.setBackAnimation(true);
                break;
            }
            case 2: {
                CFG.map.getMapCoordinates().centerToMinimapClick(Touch.getMousePosX() - this.getMenuElement(iID).getPosX() - this.getPosX(), Touch.getMousePosY() - this.getMenuElement(iID).getPosY() - this.getMenuPosY());
                break;
            }
        }
    }
    
    private final void saveDecision() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("game/decisions/" + CFG.civDecision_GameData.getTag());
            fileData.writeBytes(CFG.serialize(CFG.civDecision_GameData), false);
        }
        catch (final IOException ex2) {}
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {}
            }
        }
        try {
            FileHandle file;
            if (CFG.readLocalFiles()) {
                file = Gdx.files.local("game/decisions/Age_of_Civilizations");
            }
            else {
                file = Gdx.files.internal("game/decisions/Age_of_Civilizations");
            }
            final String tempTags = file.readString();
            if (!tempTags.contains(CFG.civDecision_GameData.getTag())) {
                final FileHandle fileSave = Gdx.files.local("game/decisions/Age_of_Civilizations");
                fileSave.writeString(tempTags + CFG.civDecision_GameData.getTag() + ";", false);
            }
            else {
                final String[] tempTagsSplited = tempTags.split(";");
                boolean tAdd = true;
                for (String s : tempTagsSplited) {
                    if (s.equals(CFG.civDecision_GameData.getTag())) {
                        tAdd = false;
                        break;
                    }
                }
                if (tAdd) {
                    final FileHandle fileSave2 = Gdx.files.local("game/decisions/Age_of_Civilizations");
                    fileSave2.writeString(tempTags + CFG.civDecision_GameData.getTag() + ";", false);
                }
            }
        }
        catch (final GdxRuntimeException ex) {
            final FileHandle fileSave3 = Gdx.files.local("game/decisions/Age_of_Civilizations");
            fileSave3.writeString(CFG.civDecision_GameData.getTag() + ";", false);
        }
    }
}
