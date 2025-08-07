// Decompiled with: Procyon 0.6.0
// Class Version: 8
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AoCGame extends ApplicationAdapter implements InputProcessor
{
    public static final boolean STEAM_BUILD = true;
    private Touch touch;
    public static int TOP;
    public static int BOTTOM;
    public static int LEFT;
    public static int RIGHT;
    protected static OrthographicCamera camera;
    protected static Viewport viewport;
    private SpriteBatch oSB;
    protected static Steam_Game steamGame;
    protected static LinkHandler mLinkHandler;
    private long lTimeFPS;
    private int iNumOfFPS;
    protected static boolean drawFPS;
    private RequestRendering requestRendering;
    protected static ShaderProgram defaultShader;
    protected static ShaderProgram extraShader;
    protected static ShaderProgram blackWhiteShader;
    protected static ShaderProgram nextPlayerTurnShader;
    protected static ShaderProgram shaderAlpha;
    protected static ShaderProgram shaderAlpha2;
    protected static boolean isShiftDown;
    protected static int introShown;
    private final String VERTEX = "attribute vec4 a_position;attribute vec4 a_color;attribute vec2 a_texCoord0;uniform mat4 u_projTrans;varying vec4 vColor;varying vec2 vTexCoord;void main() {\tvColor = a_color;\tvTexCoord = a_texCoord0;\tgl_Position =  u_projTrans * a_position;}";
    private String vertexShader;
    private String fragmentShader;
    PerspectiveCamera cam;
    CameraInputController camController;
    protected static int FPS_LIMIT;
    private long renderStart;
    private boolean MAP_MOVE_LEFT;
    private boolean MAP_MOVE_RIGHT;
    private boolean MAP_MOVE_TOP;
    private boolean MAP_MOVE_BOT;
    private static final int DEFAULT_SCROLL_MAP = 12;
    private float iScroll_MAP;
    private long lScrollTime_MAP;
    private float iScroll_MAPY;
    private long lScrollTime_MAPY;
    protected static final int TYPE_NUMBER_RESET_TIME = 625;
    protected static long TYPE_NUMER_TIME;
    protected static int TYPE_NUMBER;
    private static final int DEFAULT_SCROLL = 15;
    private int iScroll;
    private long lScrollTime;

    public AoCGame(final LinkHandler nLinkHandler) {
        this.touch = new Touch();
        this.iNumOfFPS = 0;
        this.MAP_MOVE_LEFT = false;
        this.MAP_MOVE_RIGHT = false;
        this.MAP_MOVE_TOP = false;
        this.MAP_MOVE_BOT = false;
        this.iScroll_MAP = 12.0f;
        this.lScrollTime_MAP = 0L;
        this.iScroll_MAPY = 12.0f;
        this.lScrollTime_MAPY = 0L;
        this.iScroll = 15;
        this.lScrollTime = 0L;
        AoCGame.mLinkHandler = nLinkHandler;
    }

    //static final instances for these no-ops for memory, avoid repeated allocations
    private static final RequestRendering REQUEST_RENDERING = new RequestRendering() {
        @Override
        public void update() {
        }
    };
    private final void updateRequestRendering(final boolean enable) {
        if (enable) {
            //this.requestRendering = new RequestRendering() {
            //    @Override
            //    public void update() {
            //    }
            //};
            this.requestRendering = AoCGame.REQUEST_RENDERING;
            CFG.setRender_3(true);
        }
        else {
            this.requestRendering = AoCGame.REQUEST_RENDERING;
            //this.requestRendering = new RequestRendering() {
            //    @Override
            //    public void update() {
            //    }
            //};
        }
    }
    
    private Vector2 getIOSSafeAreaInsets() {
        if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            try {
                final Class<?> IOSLauncher = Class.forName("age.of.civilizations2.jakowski.lukasz.IOSLauncher");
                return (Vector2)IOSLauncher.getDeclaredMethod("getSafeAreaInsets", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            }
            catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (final IllegalAccessException ex) {
                ex.printStackTrace();
            }
            catch (final InvocationTargetException ex2) {
                ex2.printStackTrace();
            }
            catch (final NoSuchMethodException ex3) {
                ex3.printStackTrace();
            }
        }
        return new Vector2();
    }
    
    private Vector2 getIOSSafeAreaInsets_LeftRight() {
        if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            try {
                final Class<?> IOSLauncher = Class.forName("age.of.civilizations2.jakowski.lukasz.IOSLauncher");
                return (Vector2)IOSLauncher.getDeclaredMethod("getSafeAreaInsets_LeftRight", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            }
            catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (final IllegalAccessException ex) {
                ex.printStackTrace();
            }
            catch (final InvocationTargetException ex2) {
                ex2.printStackTrace();
            }
            catch (final NoSuchMethodException ex3) {
                ex3.printStackTrace();
            }
        }
        return new Vector2();
    }

    @Override
    public void create() {
        ConfigINI.readConfig();
        CFG.LANDSCAPE = ConfigINI.landscape;
        if (CFG.isAndroid()) {
            if (CFG.LANDSCAPE) {
                CFG.GAME_WIDTH = Gdx.graphics.getWidth();
                CFG.GAME_HEIGHT = Gdx.graphics.getHeight();
            }
            else if (Gdx.graphics.getHeight() < Gdx.graphics.getWidth()) {
                CFG.GAME_WIDTH = Gdx.graphics.getHeight();
                CFG.GAME_HEIGHT = Gdx.graphics.getWidth();
            }
            else {
                CFG.GAME_WIDTH = Gdx.graphics.getWidth();
                CFG.GAME_HEIGHT = Gdx.graphics.getHeight();
            }
        }
        else {
            CFG.GAME_WIDTH = Gdx.graphics.getWidth();
            CFG.GAME_HEIGHT = Gdx.graphics.getHeight();
        }
        (AoCGame.camera = new OrthographicCamera((float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT)).setToOrtho(false, (float)CFG.GAME_WIDTH, (float)(-CFG.GAME_HEIGHT));
        AoCGame.viewport = new FitViewport((float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT, AoCGame.camera);
        CFG.updateRender(true);
        this.updateRequestRendering(true);
        CFG.loadSettings();
        CFG.DENSITY = Gdx.graphics.getDensity();
        if (CFG.DENSITY < 1.0f) {
            CFG.DENSITY = 1.0f;
        }
        if (ConfigINI.iUIScale <= 0) {
            if (CFG.isAndroid()) {
                CFG.XHDPI = (Gdx.graphics.getPpiX() >= 300.0f || CFG.GAME_WIDTH >= 1200 || CFG.GAME_HEIGHT >= 1200);
                CFG.XXHDPI = (Gdx.graphics.getPpiX() >= 380.0f || CFG.GAME_WIDTH >= 1800 || CFG.GAME_HEIGHT >= 1800);
            }
            else if (CFG.isDesktop()) {
                CFG.XHDPI = (CFG.GAME_WIDTH >= 2400);
            }
        }
        else if (ConfigINI.iUIScale == 1) {
            CFG.XHDPI = false;
            CFG.XXHDPI = false;
            CFG.XXXHDPI = false;
            CFG.XXXXHDPI = false;
        }
        else if (ConfigINI.iUIScale == 2) {
            CFG.XHDPI = true;
            CFG.XXHDPI = false;
            CFG.XXXHDPI = false;
            CFG.XXXXHDPI = false;
        }
        else if (ConfigINI.iUIScale == 3) {
            CFG.XHDPI = true;
            CFG.XXHDPI = true;
            CFG.XXXHDPI = false;
            CFG.XXXXHDPI = false;
        }
        else if (ConfigINI.iUIScale == 4) {
            CFG.XHDPI = true;
            CFG.XXHDPI = true;
            CFG.XXXHDPI = true;
            CFG.XXXXHDPI = false;
        }
        else if (ConfigINI.iUIScale == 5) {
            CFG.XHDPI = true;
            CFG.XXHDPI = true;
            CFG.XXXHDPI = true;
            CFG.XXXXHDPI = true;
        }
        this.oSB = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Images.btn_menu_h = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/menu.png");
        Images.btn_clear = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/clear.png");
        Images.btn_close = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/close.png");
        Images.diplo_war = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/war.png");
        Images.line_32_off1 = ImageManager.addImage("UI/lines/line_32_off1.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
        Images.gradient = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "gradient.png");
        Images.loading_rect_edge = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "loading/loading_edge.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
        Images.pix255_255_255 = ImageManager.addImage("UI/pix", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
        CFG.BUTTON_HEIGHT = ImageManager.getImage(Images.btn_menu_h).getHeight();
        CFG.BUTTON_WIDTH = (CFG.XXXXHDPI ? 212 : (CFG.XXXHDPI ? 180 : (CFG.XXHDPI ? 160 : (CFG.XHDPI ? 120 : 90))));
        CFG.GUI_SCALE = 100.0f * CFG.BUTTON_HEIGHT / 68.0f / 100.0f;
        CFG.PADDING = (int)(5.0f * CFG.GUI_SCALE);

        //load from setting, if not in settings default to normal, change//
        //CFG.CIV_INFO_MENU_WIDTH *= (int)CFG.GUI_SCALE;
        if (!(CFG.settingsManager.CIV_INFO_MENU_WIDTH > 0)) CFG.settingsManager.CIV_INFO_MENU_WIDTH = CFG.CIV_INFO_MENU_WIDTH;
        CFG.CIV_INFO_MENU_WIDTH = (CFG.settingsManager.CIV_INFO_MENU_WIDTH) * (int)CFG.GUI_SCALE;

        //if (!(CFG.settingsManager.PROVINCE_BORDER_SIZE < 0)) CFG.settingsManager.PROVINCE_BORDER_SIZE = 0.17F;
        if (CFG.settingsManager.PROVINCE_BORDER_COLOR == null) {
            CFG.settingsManager.PROVINCE_BORDER_COLOR = new Color_GameData(0.0F, 0.0F, 0.0F);
        } else {
            CFG.COLOR_PROVINCE_STRAIGHT.r = CFG.settingsManager.PROVINCE_BORDER_COLOR.getR();
            CFG.COLOR_PROVINCE_STRAIGHT.g = CFG.settingsManager.PROVINCE_BORDER_COLOR.getG();
            CFG.COLOR_PROVINCE_STRAIGHT.b = CFG.settingsManager.PROVINCE_BORDER_COLOR.getB();
        }


        CFG.CIV_COLOR_WIDTH *= (int)CFG.GUI_SCALE;
        CFG.SERVICE_RIBBON_WIDTH *= (int)CFG.GUI_SCALE;
        CFG.SERVICE_RIBBON_HEIGHT *= (int)CFG.GUI_SCALE;
        if (CFG.settingsManager.FONT_MAIN_SIZE < 0) {
            CFG.settingsManager.FONT_MAIN_SIZE = (int)(18.0f * CFG.GUI_SCALE);
        }
        AoCGame.updateArmyFontSize();
        Images.gameLogo = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "game_logo.png");
        Images.gameIntro = ImageManager.addImage("UI/events/AoCIntro.png");
        introShown = 1;

        //check background directory
        File folder = new File("UI/bg/");
        final File[] bgFiles = folder.listFiles();
        if (bgFiles != null && bgFiles.length > 0) {
            //only do background after asserting directory is not null
            for (File bgFile : bgFiles) {
                if (!bgFile.isFile()) continue;
                Images.backgrounds.add(ImageManager.addImage(bgFile.getPath()));
            }
            Images.background = ThreadLocalRandom.current().nextInt(0, bgFiles.length);

            Images.backgroundLast = Images.background;
            final Thread backgroundChanger = new Thread(new Runnable() {
                private volatile boolean running = true;

                @Override
                public void run() {
                    while (this.running) {
                        try {
                            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10, 16));
                            final Class<Images> clazz = Images.class;
                            Images.backgroundLast = Images.background;
                            Images.background = ThreadLocalRandom.current().nextInt(0, bgFiles.length);
                            for (float alpha = 0.0f; alpha < 1.0f; alpha += 0.01f) {
                                final Class<Images> clazz2 = Images.class;
                                Images.backgroundAlpha = alpha;
                                TimeUnit.MILLISECONDS.sleep(25L);
                            }
                            continue;
                        } catch (final InterruptedException e) {
                            this.running = false;
                            Thread.currentThread().interrupt();
                            continue;
                        }
                    }
                }

                public void stop() {
                    this.running = false;
                }
            });
            backgroundChanger.start();
        }

        CFG.menuManager = new MenuManager();
        Game_Render.updateRenderer();
        Game_Render.updateDrawMoveUnits();
        CFG.soundsManager = new SoundsManager();
        new InitGame();
        ShaderProgram.pedantic = false;
        final String defaultVertex = Gdx.files.internal("game/shader/default_vertex.glsl").readString();
        final String flagFragment = Gdx.files.internal("game/shader/flag_fragment.glsl").readString();
        final String nextPlayerTurnVertex = Gdx.files.internal("game/shader/nextPlayerTurn_vertex.glsl").readString();
        (AoCGame.shaderAlpha = new ShaderProgram("attribute vec4 a_position;attribute vec4 a_color;attribute vec2 a_texCoord0;uniform mat4 u_projTrans;varying vec4 vColor;varying vec2 vTexCoord;void main() {\tvColor = a_color;\tvTexCoord = a_texCoord0;\tgl_Position =  u_projTrans * a_position;}", flagFragment)).begin();
        AoCGame.shaderAlpha.setUniformi("u_texture1", 1);
        AoCGame.shaderAlpha.setUniformi("u_mask", 2);
        AoCGame.shaderAlpha.end();
        this.vertexShader = "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_color.a = v_color.a * (255.0/254.0);\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projTrans * a_position;\n}\n";
        this.fragmentShader = "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nuniform sampler2D u_texture2;\nuniform float u_maskScale;\nuniform float u_useMask;\nuniform vec2 u_maskOffset;\nvoid main()                                  \n{                                            \n vec2 newCoords = -1.0 * (u_maskScale - 1.0)/2.0 + (u_maskScale * v_texCoords) + u_maskOffset;\n vec4 mask = vec4(1.0, 1.0, 1.0, 1.0); \nif(u_useMask > 0.5) \n\tmask = texture2D(u_texture2, v_texCoords);\n vec4 color = v_color * texture2D(u_texture, newCoords);\n  gl_FragColor = vec4(color.rgb, color.a * mask.r);\n}";
        (AoCGame.shaderAlpha2 = new ShaderProgram(this.vertexShader, this.fragmentShader)).begin();
        AoCGame.shaderAlpha2.setUniformi("u_texture", 0);
        AoCGame.shaderAlpha2.setUniformi("u_texture2", 1);
        AoCGame.shaderAlpha2.setUniformf("u_useMask", 1.0f);
        AoCGame.shaderAlpha2.setUniformf("u_maskScale", 20.0f);
        AoCGame.shaderAlpha2.setUniformf("u_maskOffset", 0.0f, 0.0f);
        final String defaultFragment = Gdx.files.internal("game/shader/default_fragment.glsl").readString();

        String extraFragment;
        try {
            extraFragment = Gdx.files.internal("game/shader/twopointfive_fragment.glsl").readString();
            AoCGame.extraShader = new ShaderProgram(defaultVertex, extraFragment);
        } catch (GdxRuntimeException | NullPointerException ex) {
            AoCGame.extraShader = null;
        }

        final String blackWhiteFragment = Gdx.files.internal("game/shader/blackWhite_fragment.glsl").readString();
        final String nextPlayerTurnFragment = Gdx.files.internal("game/shader/nextPlayerTurn_fragment.glsl").readString();
        AoCGame.defaultShader = new ShaderProgram(defaultVertex, defaultFragment);
        AoCGame.blackWhiteShader = new ShaderProgram(defaultVertex, blackWhiteFragment);
        AoCGame.nextPlayerTurnShader = new ShaderProgram(nextPlayerTurnVertex, nextPlayerTurnFragment);
        final long time = System.currentTimeMillis();
        loadCursor(true);
        Gdx.app.log("AoC", "LOAD TIME: " + (System.currentTimeMillis() - time));
    }
    
    protected static final void loadCursor(final boolean inInit) {
        if (CFG.settingsManager.loadCursor) {
            try {
                final Pixmap pixmap = new Pixmap(Gdx.files.internal("UI/icons/cursor.png"));
                final Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
                Gdx.graphics.setCursor(cursor);
                pixmap.dispose();
            }
            catch (final GdxRuntimeException ex) {}
        }
        else if (!inInit) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }
    
    protected static final void updateArmyFontSize() {
        if (CFG.settingsManager.FONT_ARMY_SIZE < 0) {
            if (CFG.XXXHDPI || CFG.XXXXHDPI || CFG.XXHDPI) {
                CFG.settingsManager.FONT_ARMY_SIZE = 18;
            }
            else {
                CFG.settingsManager.FONT_ARMY_SIZE = 16;
            }
        }
    }
    
    public void update() {
        this.countFPS();
        try {
            CFG.game.update();
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final IndexOutOfBoundsException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
        catch (final StackOverflowError ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
        catch (final ArithmeticException ex4) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex4);
            }
        }
        try {
            CFG.map.update();
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final IndexOutOfBoundsException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
        catch (final StackOverflowError ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
        catch (final ArithmeticException ex4) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex4);
            }
        }
        try {
            CFG.menuManager.update();
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final IndexOutOfBoundsException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
        catch (final StackOverflowError ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
        catch (final ArithmeticException ex4) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex4);
            }
        }
    }
    
    @Override
    public void render() {
        this.renderStart = System.currentTimeMillis();
        try {
            this.update();
            this.updateMoveMap();
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final ArithmeticException ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final StackOverflowError ex4) {
            CFG.exceptionStack(ex4);
        }
        catch (final IllegalArgumentException ex5) {
            CFG.exceptionStack(ex5);
        }
        if (!CFG.RENDER && !CFG.settingsManager.CONTINUOUS_RENDERING) {
            try {
                Thread.sleep((long)(1000.0f / AoCGame.FPS_LIMIT - (System.currentTimeMillis() - this.renderStart)));
            }
            catch (final InterruptedException e) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(e);
                }
            }
            catch (final IllegalArgumentException e2) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(e2);
                }
            }
            catch (final NullPointerException e3) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(e3);
                }
            }
        }
        else {
            //this is such a terrible method but whatever
            if (introShown > 0) {
                try {
                    float alpha;
                    if (introShown <= 175) {
                        alpha = Math.min(0.01F * (introShown * 1.25F), 1.0F);
                        if (introShown == 40) {
                            CFG.soundsManager.playEventMusic("UI/events/AoCIntro.mp3", true);
                        }
                    } else {
                        alpha = Math.max((100.0F - ((introShown) - 175) * 2) / 100.0F, 0.0F);
                        if (alpha == 0.0F) {
                            //if start music detected
                            if (!Objects.equals(SoundsManager.START_MUSIC, "")) {
                                CFG.soundsManager.playStartMusic();
                            } else {
                                CFG.soundsManager.loadNextMusic();
                            }
                            introShown = -1;
                            return;
                        }
                    }

                    Gdx.gl.glClearColor(CFG.BACKGROUND_COLOR.r, CFG.BACKGROUND_COLOR.g, CFG.BACKGROUND_COLOR.b, CFG.BACKGROUND_COLOR.a);
                    Gdx.gl.glClear(16640);
                    AoCGame.viewport.setWorldSize(CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale(), CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale());
                    AoCGame.viewport.apply();
                    AoCGame.camera.setToOrtho(true, CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale(), -CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale());
                    this.oSB.setProjectionMatrix(AoCGame.camera.combined);

                    oSB.begin();
                    oSB.setColor(new Color(1.0F, 1.0F, 1.0F, (float) 255));
                    oSB.setColor(1.0F, 1.0F, 1.0F, alpha);
                    ImageManager.getImage(Images.gameIntro).draw2(oSB, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
                    oSB.setColor(new Color(1.0F, 1.0F, 1.0F, (float) 255));
                    oSB.setColor(1.0F, 1.0F, 1.0F, alpha);
                    oSB.draw(ImageManager.getImage(Images.gameIntro).getTexture(), 0.0F, (float) (-CFG.GAME_HEIGHT), (float) CFG.GAME_WIDTH, (float) CFG.GAME_HEIGHT);
                    oSB.end();

                    introShown += 1;
                    return;
                } catch (final Exception ex6) {
                    //if start music detected
                    if (!Objects.equals(SoundsManager.START_MUSIC, "")) {
                        CFG.soundsManager.playStartMusic();
                    } else {
                        CFG.soundsManager.loadNextMusic();
                    }
                    introShown = -1;
                    return;
                }
            }

            try {
                if (CFG.RENDER3) {
                    CFG.RENDER3 = false;
                }
                else if (CFG.RENDER2) {
                    CFG.RENDER2 = false;
                }
                else {
                    CFG.RENDER = false;
                }
                FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, false);
                fbo.begin();

                Gdx.gl.glClearColor(CFG.BACKGROUND_COLOR.r, CFG.BACKGROUND_COLOR.g, CFG.BACKGROUND_COLOR.b, CFG.BACKGROUND_COLOR.a);
                Gdx.gl.glClear(16640);
                AoCGame.viewport.setWorldSize(CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale(), CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale());
                AoCGame.viewport.apply();
                AoCGame.camera.setToOrtho(true, CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale(), -CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale());
                this.oSB.setProjectionMatrix(AoCGame.camera.combined);
                this.oSB.begin();
                this.oSB.setShader(AoCGame.defaultShader);
                Game_Render.draw(this.oSB);
                this.oSB.end();
                AoCGame.camera.setToOrtho(false, (float)CFG.GAME_WIDTH, (float)(-CFG.GAME_HEIGHT));
                AoCGame.viewport.setWorldSize((float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT);
                AoCGame.viewport.apply();
                this.oSB.setProjectionMatrix(AoCGame.camera.combined);
                this.oSB.begin();
                Game_Render.drawWithoutScale(this.oSB);
                this.oSB.end();
                AoCGame.viewport.setWorldSize(CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale(), CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale());
                AoCGame.viewport.apply();
                AoCGame.camera.setToOrtho(true, CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale(), -CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale());
                this.oSB.setProjectionMatrix(AoCGame.camera.combined);
                this.oSB.begin();
                this.oSB.setShader(AoCGame.defaultShader);
                Game_Render.drawMapDetails(this.oSB);
                this.oSB.end();
                AoCGame.camera.setToOrtho(false, (float)CFG.GAME_WIDTH, (float)(-CFG.GAME_HEIGHT));
                AoCGame.viewport.setWorldSize((float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT);
                AoCGame.viewport.apply();
                this.oSB.setProjectionMatrix(AoCGame.camera.combined);
                this.oSB.begin();
                this.oSB.setColor(Color.WHITE);
                CFG.menuManager.draw(this.oSB);
                CFG.editorManager.draw(this.oSB);
                if (AoCGame.drawFPS) {
                    try {
                        CFG.fontMain.getData().setScale(0.8f);
                        CFG.drawTextWithShadow(this.oSB, "FPS: " + CFG.iNumOfFPS, CFG.PADDING * 2, CFG.PADDING * 2, Color.WHITE);
                        CFG.fontMain.getData().setScale(1.0f);
                    }
                    catch (final NullPointerException | IllegalStateException ex7) {}
                }
                this.oSB.setColor(Color.WHITE);
                this.oSB.end();
                fbo.end();

                //draw post-processing effect
                AoCGame.camera.setToOrtho(false, (float)CFG.GAME_WIDTH, (float)(CFG.GAME_HEIGHT));
                AoCGame.viewport.setWorldSize((float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT);
                AoCGame.viewport.apply();
                this.oSB.setProjectionMatrix(AoCGame.camera.combined);
                this.oSB.begin();
                if (AoCGame.extraShader != null) {
                    this.oSB.setShader(AoCGame.extraShader);
                }
                this.oSB.draw(fbo.getColorBufferTexture(), 0.0f, 0.0f, (float)CFG.GAME_WIDTH, (float)(CFG.GAME_HEIGHT), 0, 0, 1, 1);
                this.oSB.end();
                fbo.dispose();
            }
            catch (final IllegalStateException ex6) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex6);
                }
                CFG.setRender_3(true);
                try {
                    this.oSB.end();
                }
                catch (final IllegalStateException ex9) {}
            }
            catch (final NullPointerException ex2) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex2);
                }
                CFG.setRender_3(true);
            }
            catch (final IndexOutOfBoundsException ex) {
                CFG.exceptionStack(ex);
                CFG.setRender_3(true);
            }
            catch (final StackOverflowError ex4) {
                CFG.exceptionStack(ex4);
                CFG.setRender_3(true);
            }
            catch (final ArithmeticException ex3) {
                CFG.exceptionStack(ex3);
                CFG.setRender_3(true);
            }
            catch (final IllegalArgumentException ex5) {
                CFG.exceptionStack(ex5);
                CFG.setRender_3(true);
            }
        }
        if (CFG.isDesktop()) {}
        this.requestRendering.update();
    }
    
    private void countFPS() {
        ++this.iNumOfFPS;
        if (System.currentTimeMillis() > this.lTimeFPS + 1000L) {
            this.lTimeFPS = System.currentTimeMillis();
            CFG.iNumOfFPS = this.iNumOfFPS;
            this.iNumOfFPS = 0;
        }
    }
    
    @Override
    public void resize(final int width, final int height) {
        if (CFG.isAndroid()) {
            if (CFG.LANDSCAPE) {
                AoCGame.viewport.update(width, height, false);
            }
            else {
                AoCGame.viewport.update(-height, -width, false);
            }
        }
        else {
            AoCGame.viewport.update(width, height, false);
        }
        CFG.setRender_3(true);
    }
    
    @Override
    public void dispose() {
        try {
            Gdx.app.log("AoC", "dispose");
            if (CFG.isDesktop()) {}
            this.oSB.dispose();
            CFG.fontMain.dispose();
            CFG.fontBorder.dispose();
            for (int i = 0; i < ImageManager.getImagesSize(); ++i) {
                ImageManager.getImage(i).getTexture().dispose();
            }
            CFG.map.getMapBG().disposeGameMap();
            CFG.soundsManager.dispose();
        }
        catch (final GdxRuntimeException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final NullPointerException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
    }
    
    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        try {
            Touch.setMousePosXY(screenX, screenY);
            CFG.setRender_3(true);
            this.touch.actionDown(screenX, screenY, pointer);
            CFG.editorManager.touchDown(screenX, screenY, pointer, button);
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return true;
    }
    
    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        try {
            Touch.setMousePosXY(screenX, screenY);
            if (Gdx.input.isTouched(1) && pointer == 0) {
                this.touch.actionMove(Gdx.input.getX(0), Gdx.input.getY(0), Gdx.input.getX(1), Gdx.input.getY(1));
            }
            else {
                this.touch.actionMove(screenX, screenY, pointer);
            }
            CFG.editorManager.touchDragged(screenX, screenY, pointer);
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return true;
    }
    
    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        try {
            Touch.setMousePosXY(screenX, screenY);
            CFG.setRender_3(true);
            try {
                if (CFG.isDesktop()) {
                    if (CFG.menuManager.getInGameView() && CFG.map.getMapScale().getCurrentScale() >= 1.0f) {
                        if (button == 1 && CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS && (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE && CFG.game.getActiveProvinceID() >= 0 && CFG.gameAction.controlsArmyInProvince(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.SPECTATOR_MODE) && !CFG.menuManager.getVisible_InGame_FlagAction()) {
                            CFG.game.setProvinceID_PPM(screenX, screenY);
                        }
                        else {
                            this.touch.actionUp(screenX, screenY, pointer);
                        }
                    }
                    else {
                        this.touch.actionUp(screenX, screenY, pointer);
                    }
                }
                else {
                    this.touch.actionUp(screenX, screenY, pointer);
                }
            }
            catch (final NullPointerException ex) {
                this.touch.actionUp(screenX, screenY, pointer);
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (final IndexOutOfBoundsException ex2) {
                this.touch.actionUp(screenX, screenY, pointer);
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex2);
                }
            }
            CFG.editorManager.touchUp(screenX, screenY, pointer, button);
        }
        catch (final IndexOutOfBoundsException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final NullPointerException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return true;
    }
    
    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        try {
            Touch.setMousePosXY(screenX, screenY);
            if (CFG.isDesktop()) {
                if (screenX < CFG.PADDING) {
                    if (!this.MAP_MOVE_LEFT) {
                        this.MAP_MOVE_LEFT = true;
                        this.MAP_MOVE_RIGHT = false;
                        this.lScrollTime_MAP = System.currentTimeMillis();
                        this.iScroll_MAP = 15.0f;
                    }
                }
                else {
                    this.MAP_MOVE_LEFT = false;
                }
                if (screenX > CFG.GAME_WIDTH - CFG.PADDING) {
                    if (!this.MAP_MOVE_RIGHT) {
                        this.MAP_MOVE_RIGHT = true;
                        this.MAP_MOVE_LEFT = false;
                        this.lScrollTime_MAP = System.currentTimeMillis();
                        this.iScroll_MAP = 15.0f;
                    }
                }
                else {
                    this.MAP_MOVE_RIGHT = false;
                }
                if (screenY < CFG.PADDING) {
                    if (!this.MAP_MOVE_TOP) {
                        this.MAP_MOVE_TOP = true;
                        this.MAP_MOVE_BOT = false;
                        this.lScrollTime_MAPY = System.currentTimeMillis();
                        this.iScroll_MAPY = 15.0f;
                    }
                }
                else {
                    this.MAP_MOVE_TOP = false;
                }
                if (screenY > CFG.GAME_HEIGHT - CFG.PADDING) {
                    if (!this.MAP_MOVE_BOT) {
                        this.MAP_MOVE_BOT = true;
                        this.MAP_MOVE_TOP = false;
                        this.lScrollTime_MAPY = System.currentTimeMillis();
                        this.iScroll_MAPY = 15.0f;
                    }
                }
                else {
                    this.MAP_MOVE_BOT = false;
                }
            }
            this.touch.actionMove_Hover(screenX, screenY);
            return true;
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return true;
    }
    
    private final void updateMoveMap() {
        try {
            if (this.MAP_MOVE_LEFT) {
                this.updateScroll_Map();
                CFG.map.getMapCoordinates().setNewPosX(CFG.map.getMapCoordinates().getPosX() + (int)this.iScroll_MAP);
            }
            else if (this.MAP_MOVE_RIGHT) {
                this.updateScroll_Map();
                CFG.map.getMapCoordinates().setNewPosX(CFG.map.getMapCoordinates().getPosX() - (int)this.iScroll_MAP);
            }
            if (this.MAP_MOVE_TOP) {
                this.updateScroll_MapY();
                CFG.map.getMapCoordinates().setNewPosY(CFG.map.getMapCoordinates().getPosY() + (int)this.iScroll_MAPY);
            }
            else if (this.MAP_MOVE_BOT) {
                this.updateScroll_MapY();
                CFG.map.getMapCoordinates().setNewPosY(CFG.map.getMapCoordinates().getPosY() - (int)this.iScroll_MAPY);
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final NullPointerException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
        catch (final ArithmeticException ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
    }
    
    private final void updateScroll_Map() {
        if (this.lScrollTime_MAP + 150L < System.currentTimeMillis()) {
            this.lScrollTime_MAP = System.currentTimeMillis();
            this.iScroll_MAP += this.iScroll_MAP * 0.475f;
            if (this.iScroll_MAP > 35.0f * ((CFG.map.getMapScale().getCurrentScale() < 1.0f) ? (1.0f + (1.0f - CFG.map.getMapScale().getCurrentScale())) : 1.0f)) {
                this.iScroll_MAP = 35.0f * ((CFG.map.getMapScale().getCurrentScale() < 1.0f) ? (1.0f + (1.0f - CFG.map.getMapScale().getCurrentScale())) : 1.0f);
            }
        }
    }
    
    private final void updateScroll_MapY() {
        if (this.lScrollTime_MAPY + 150L < System.currentTimeMillis()) {
            this.lScrollTime_MAPY = System.currentTimeMillis();
            this.iScroll_MAPY += this.iScroll_MAPY * 0.475f;
            if (this.iScroll_MAPY > 35.0f * ((CFG.map.getMapScale().getCurrentScale() < 1.0f) ? (1.0f + (1.0f - CFG.map.getMapScale().getCurrentScale())) : 1.0f)) {
                this.iScroll_MAPY = 35.0f * ((CFG.map.getMapScale().getCurrentScale() < 1.0f) ? (1.0f + (1.0f - CFG.map.getMapScale().getCurrentScale())) : 1.0f);
            }
        }
    }

    //For reference: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L68
    @Override
    public boolean keyDown(final int keycode) {
        try {
            CFG.setRender_3(true);
            //skip intro w space
            if (introShown > 0 && keycode == 62) {
                if (!Objects.equals(SoundsManager.START_MUSIC, "")) {
                    CFG.soundsManager.playStartMusic();
                } else {
                    CFG.soundsManager.loadNextMusic();
                }
                introShown = -1;
            }
            if (!CFG.menuManager.getKeyboard().getVisible()) {
                if (CFG.editorManager.keyDown(keycode)) {
                    return true;
                }
                if (keycode == 21) {
                    this.MAP_MOVE_LEFT = true;
                    this.MAP_MOVE_RIGHT = false;
                    this.lScrollTime_MAP = System.currentTimeMillis();
                    this.iScroll_MAP = 15.0f;
                }
                if (keycode == 22) {
                    this.MAP_MOVE_RIGHT = true;
                    this.MAP_MOVE_LEFT = false;
                    this.lScrollTime_MAP = System.currentTimeMillis();
                    this.iScroll_MAP = 15.0f;
                }
                if (keycode == 19) {
                    this.MAP_MOVE_TOP = true;
                    this.MAP_MOVE_BOT = false;
                    this.lScrollTime_MAPY = System.currentTimeMillis();
                    this.iScroll_MAPY = 15.0f;
                }
                if (keycode == 20) {
                    this.MAP_MOVE_BOT = true;
                    this.MAP_MOVE_TOP = false;
                    this.lScrollTime_MAPY = System.currentTimeMillis();
                    this.iScroll_MAPY = 15.0f;
                }
                if (keycode == 59) {
                    AoCGame.isShiftDown = true;
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return true;
    }
    
    protected final void typeNumber(final int iNum) {
        if (System.currentTimeMillis() - 625L > AoCGame.TYPE_NUMER_TIME) {
            AoCGame.TYPE_NUMBER = iNum;
        }
        else {
            AoCGame.TYPE_NUMBER *= 10;
            AoCGame.TYPE_NUMBER += iNum;
        }
        AoCGame.TYPE_NUMER_TIME = System.currentTimeMillis();
    }
    
    protected static final void resetTypeNumber() {
        AoCGame.TYPE_NUMER_TIME = 0L;
        AoCGame.TYPE_NUMBER = 0;
    }
    
    @Override
    public boolean keyUp(final int keycode) {
        CFG.setRender_3(true);
        try {
            if (!CFG.menuManager.getKeyboard().getVisible()) {
                if (CFG.editorManager.keyUp(keycode)) {
                    return true;
                }
                if (keycode == 21) {
                    this.MAP_MOVE_LEFT = false;
                }
                if (keycode == 22) {
                    this.MAP_MOVE_RIGHT = false;
                }
                if (keycode == 19) {
                    this.MAP_MOVE_TOP = false;
                }
                if (keycode == 20) {
                    this.MAP_MOVE_BOT = false;
                }
                if (keycode == 59) {
                    AoCGame.isShiftDown = false;
                }
                if (CFG.isDesktop()) {
                    if (CFG.menuManager.getDialogMenu().getVisible()) {
                        if (keycode == 66 || keycode == 62) {
                            CFG.menuManager.getDialogMenu().getMenuElement(1).setClickable(false);
                            CFG.menuManager.getDialogMenu().getMenuElement(2).setClickable(false);
                            CFG.dialog_True();
                            CFG.menuManager.getDialogMenu().onBackPressed();
                        }
                        else if (keycode == 131 || keycode == 67) {
                            CFG.menuManager.getDialogMenu().getMenuElement(1).setClickable(false);
                            CFG.menuManager.getDialogMenu().getMenuElement(2).setClickable(false);
                            CFG.dialog_False();
                            CFG.menuManager.getDialogMenu().onBackPressed();
                        }
                    }
                    else if (CFG.menuManager.getInGameView()) {
                        Gdx.app.log("AoC2.5", "In-Game Character:" + String.valueOf(keycode));
                        if (keycode == 3) {
                            try {
                                CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
                                CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getActiveProvinceID());
                                if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE) {
                                    CFG.game.disableDrawCivilizationRegions_Active();
                                    CFG.game.enableDrawCivilizationRegions_ActiveProvince();
                                }
                                if (CFG.menuManager.getVisible_InGame_FlagAction()) {
                                    CFG.menuManager.setVisible_InGame_FlagAction(false);
                                }
                                if (CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getName().length() > 0) {
                                    CFG.toast.setInView(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                                }
                            }
                            catch (final IndexOutOfBoundsException ex) {
                                if (CFG.LOGS) {
                                    CFG.exceptionStack(ex);
                                }
                            }
                        }
                        else if (keycode == 131) {
                            if (CFG.menuManager.getVisible_InGame_FlagAction()) {
                                Menu_InGame.clickFlagAction();
                            }
                            else if (CFG.regroupArmyMode) {
                                CFG.game.resetRegroupArmyData();
                                CFG.game.checkProvinceActionMenu();
                            }
                            else if (CFG.chooseProvinceMode || CFG.chosenProvinceID >= 0) {
                                CFG.game.resetChooseProvinceData();
                                CFG.game.checkProvinceActionMenu();
                            }
                            else if (CFG.menuManager.getInGameView_Options()) {
                                Menu_InGame_Options.clickBack();
                            }
                            else if (CFG.menuManager.getInGame_ProvinceRecruit_Visible()) {
                                CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
                                CFG.game.checkProvinceActionMenu();
                            }
                            else if (CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible()) {
                                CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(false);
                                CFG.game.checkProvinceActionMenu();
                            }
                            else if (CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
                                CFG.game.checkProvinceActionMenu();
                            }
                            else {
                                Menu_InGame_FlagAction.clickOptions();
                            }
                        }
                        else if (keycode == 31) {
                            Game_Render.DISABLE_CITIES = !Game_Render.DISABLE_CITIES;
                            CFG.toast.setInView("C, " + CFG.langManager.get("Cities"), Game_Render.DISABLE_CITIES ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                            CFG.toast.setTimeInView(4500);
                        }
                        else if (keycode == 50) {
                            Game_Render.DISABLE_CIVS_NAMES = !Game_Render.DISABLE_CIVS_NAMES;
                            CFG.toast.setInView("V, " + CFG.langManager.get("CivilizationsNames"), Game_Render.DISABLE_CIVS_NAMES ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                            CFG.toast.setTimeInView(4500);
                        }
                        else if (keycode == 69) {
                            RTS.updateSpeed(-1);
                        }
                        else if (keycode == 81) {
                            RTS.updateSpeed(1);
                        }
                        //battle toggle, if b
                        else if (keycode == 30) {
                            //if not between turns and not spec mode
                            if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS && !CFG.SPECTATOR_MODE) {
                                RTS.updateShowBattles();
                            }
                        }
                        //toggle spec mode, if shift + s
                        else if (isShiftDown && keycode == 47) {
                            if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
                                Gdx.app.log("AoC2.5", "Toggle spectator mode: " + CFG.SPECTATOR_MODE);
                                CFG.SPECTATOR_MODE = !CFG.SPECTATOR_MODE;
                                CFG.toast.setInView("SHIFT + S, " + CFG.langManager.get("ESSM"), CFG.SPECTATOR_MODE ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);

                                //added refresh
                                if (CFG.menuManager.getVisible_InGame_CivInfo()) {
                                    CFG.setActiveCivInfo(CFG.getActiveCivInfo());
                                }
                                CFG.updateActiveCivInfo_InGame();

                                if (!CFG.SPECTATOR_MODE) {
                                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                                    CFG.menuManager.setVisible_Menu_InGame_CurrentWars(true);

                                    CFG.game.getPlayer(CFG.PLAYER_TURNID).buildMetProvincesAndCivs();
                                    CFG.gameAction.buildFogOfWar(CFG.PLAYER_TURNID);
                                    Game_Render.updateDrawCivRegionNames_FogOfWar();

                                    for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                                        CFG.game.getProvince(i).updateProvinceBorder();
                                    }
                                    CFG.game.updateActiveProvinceBorderStyle();
                                } else {
                                    CFG.game.getPlayer(CFG.PLAYER_TURNID).initMetProvince(true);
                                    CFG.game.getPlayer(CFG.PLAYER_TURNID).initMetCivilization(true);
                                    Game_Render.updateDrawCivRegionNames_FogOfWar();

                                    for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                                        CFG.game.getProvince(i).updateProvinceBorder();
                                    }
                                    CFG.game.updateActiveProvinceBorderStyle();
                                }
                            }
                        }
                        else if (keycode == 66) {
                            if (!CFG.menuManager.getInGameView_Options() && CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS && (CFG.menuManager.getInGame_ProvinceMoveUnits_Visible() || CFG.menuManager.getInGame_ProvinceRecruit_Visible() || CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible() || CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible() || CFG.menuManager.getInGame_ProvinceDisband_Visible())) {
                                if (CFG.menuManager.getInGame_ProvinceMoveUnits_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceMoveUnits_Confrim();
                                }
                                if (CFG.menuManager.getInGame_ProvinceRecruit_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceRecruit_Confrim();
                                }
                                if (CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceRecruitInstantly_Confrim();
                                }
                                if (CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceRegroupArmy_ConfirmMove();
                                }
                                if (CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceDisband_Confrm();
                                }
                            }
                            else {
                                RTS.pauseUnpause();
                            }
                        }
                        else if (!CFG.menuManager.getInGameView_Options()) {
                            if (keycode == 62) {
                                if (CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceRegroupArmy_ConfirmMove();
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_MOVE_REGROUP);
                                    return true;
                                }
                                if (CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceDisband_Confrm();
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK2);
                                    return true;
                                }
                                if (CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceRecruitInstantly_Confrim();
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_RECRUIT);
                                    return true;
                                }
                                if (CFG.menuManager.getInGame_ProvinceRecruit_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceRecruit_Confrim();
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_RECRUIT);
                                    return true;
                                }
                                if (CFG.menuManager.getInGame_ProvinceMoveUnits_Visible()) {
                                    CFG.menuManager.getInGame_ProvinceMoveUnits_Confrim();
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_MOVE_ARMY);
                                    return true;
                                }
                                if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
                                    RTS.PAUSE = true;
                                    RTS.resetTime();
                                }
                                try {
                                    if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).getClickable()) {
                                        Menu_InGame_ProvinceInfo.clickEndTurn();
                                    }
                                }
                                catch (final NullPointerException ex2) {
                                    CFG.exceptionStack(ex2);
                                }
                                catch (final IndexOutOfBoundsException ex) {
                                    CFG.exceptionStack(ex);
                                }
                            }
                            if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
                                if (CFG.menuManager.getInGame_ProvinceMoveUnits_Visible() || CFG.menuManager.getInGame_ProvinceRecruit_Visible() || CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible() || CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible() || CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                    if (keycode == 7 || keycode == 144) {
                                        this.typeNumber(0);
                                    }
                                    else if (keycode == 8 || keycode == 145) {
                                        this.typeNumber(1);
                                    }
                                    else if (keycode == 9 || keycode == 146) {
                                        this.typeNumber(2);
                                    }
                                    else if (keycode == 10 || keycode == 147) {
                                        this.typeNumber(3);
                                    }
                                    else if (keycode == 11 || keycode == 148) {
                                        this.typeNumber(4);
                                    }
                                    else if (keycode == 12 || keycode == 149) {
                                        this.typeNumber(5);
                                    }
                                    else if (keycode == 13 || keycode == 150) {
                                        this.typeNumber(6);
                                    }
                                    else if (keycode == 14 || keycode == 151) {
                                        this.typeNumber(7);
                                    }
                                    else if (keycode == 15 || keycode == 152) {
                                        this.typeNumber(8);
                                    }
                                    else if (keycode == 16 || keycode == 153) {
                                        this.typeNumber(9);
                                    }
                                    if (CFG.menuManager.getInGame_ProvinceMoveUnits_Visible()) {
                                        CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setCurrent(AoCGame.TYPE_NUMBER);
                                        CFG.menuManager.updateInGame_ActionInfo_Move();
                                    }
                                    if (CFG.menuManager.getInGame_ProvinceRecruit_Visible()) {
                                        CFG.menuManager.getInGame_ProvinceRecruit_Slider().setCurrent(AoCGame.TYPE_NUMBER);
                                        CFG.menuManager.updateInGame_ActionInfo_Recruit();
                                    }
                                    if (CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible()) {
                                        CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setCurrent(AoCGame.TYPE_NUMBER);
                                        CFG.menuManager.updateInGame_ActionInfo_RecruitInstantly();
                                    }
                                    if (CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible()) {
                                        CFG.menuManager.getInGame_RegroupArmy_Slider().setCurrent(AoCGame.TYPE_NUMBER);
                                        CFG.menuManager.updateInGame_ActionInfo_Regroup();
                                    }
                                    if (CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                        CFG.menuManager.getInGame_ProvinceDisband_Slider().setCurrent(AoCGame.TYPE_NUMBER);
                                        CFG.menuManager.updateInGame_ActionInfo_Disband();
                                    }
                                }
                                if (keycode == 77) {
                                    CFG.menuManager.setVisible_InGame_FlagAction_Console(!CFG.menuManager.getVisible_InGame_FlagAction_Console());
                                }
                                if (keycode == 244) {
                                    Menu_InGame.clickFlagAction();
                                }
                                else if (keycode == 245 || keycode == 61) {
                                    if (CFG.menuManager.getVisible_InGame_FlagAction()) {
                                        Menu_InGame.clickFlagAction();
                                    }
                                    CFG.menuManager.setVisible_InGame_CivInfo(!CFG.menuManager.getVisible_InGame_CivInfo());
                                    CFG.menuManager.setVisible_InGame_CivManage(!CFG.menuManager.getVisible_InGame_ManageInfo());
                                }
                                else if (keycode == 246) {
                                    CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_DIPLOMACY_MODE);
                                }
                                else if (keycode == 247) {
                                    CFG.menuManager.setVisible_InGame_MapModes(!CFG.menuManager.getInGame_MapModes().getVisible());
                                    if (CFG.menuManager.getInGame_MapModes().getPosX() < 0) {
                                        CFG.menuManager.getInGame_MapModes().setPosX_Force(CFG.menuManager.getInGame().getMenuElement(9).getPosX() + CFG.menuManager.getInGame().getMenuElement(9).getWidth() / 2 - CFG.menuManager.getInGame_MapModes().getWidth() / 2);
                                        CFG.menuManager.getInGame_MapModes().setPosY(CFG.menuManager.getInGame_MapModes().getTitle().getHeight() + CFG.menuManager.getInGame().getMenuElement(9).getPosY() + CFG.menuManager.getInGame().getMenuElement(9).getHeight() + CFG.PADDING);
                                        if (CFG.menuManager.getInGame_MapModes().getPosX() + CFG.menuManager.getInGame_MapModes().getWidth() > CFG.GAME_WIDTH - CFG.PADDING) {
                                            CFG.menuManager.getInGame_MapModes().setPosX_Force(CFG.GAME_WIDTH - CFG.PADDING - CFG.menuManager.getInGame_MapModes().getWidth());
                                        }
                                    }
                                }
                                else if (keycode == 248) {
                                    if (CFG.menuManager.getVisible_Menu_InGame_Outliner()) {
                                        CFG.menuManager.setVisible_Menu_InGame_Outliner(false);
                                    }
                                    else {
                                        Menu_InGame_FlagAction.clickStats();
                                    }
                                }
                                else if (keycode == 249) {
                                    if (CFG.menuManager.getVisibleInGame_Wars()) {
                                        CFG.menuManager.setVisibleInGame_Wars(false);
                                    }
                                    else {
                                        CFG.menuManager.rebuildInGame_Wars();
                                    }
                                }
                                else if (keycode == 250) {
                                    if (CFG.menuManager.getVisibleInGame_MilitaryAlliances()) {
                                        CFG.menuManager.setVisibleInGame_MilitaryAlliances(false);
                                    }
                                    else {
                                        CFG.menuManager.rebuildInGame_MilitaryAlliances();
                                    }
                                }
                                else if (keycode == 251) {
                                    if (CFG.menuManager.getVisibleInGame_History()) {
                                        CFG.menuManager.setVisibleInGame_History(false);
                                    }
                                    else {
                                        CFG.menuManager.rebuildInGame_History();
                                    }
                                }
                                else if (keycode == 252) {
                                    if (CFG.menuManager.getVisibleInGame_Rank()) {
                                        CFG.menuManager.setVisibleInGame_Rank(false);
                                    }
                                    else {
                                        CFG.menuManager.rebuildInGame_Rank();
                                    }
                                }
                                else if (keycode == 255) {
                                    CFG.menuManager.setVisibleInGame_Playlist(!CFG.menuManager.getVisibleInGame_Playlist());
                                }
                                else if (keycode != 8) {
                                    if (keycode == 45) {
                                        if (CFG.chooseProvinceMode) {
                                            CFG.game.resetChooseProvinceData();
                                            CFG.game.checkProvinceActionMenu();
                                            return true;
                                        }
                                    }
                                    else if (keycode == 51) {
                                        if (CFG.menuManager.getInGame_ProvinceRecruit_Visible()) {
                                            CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
                                            CFG.game.checkProvinceActionMenu();
                                            return true;
                                        }
                                    }
                                    else if (keycode == 33) {
                                        if (CFG.menuManager.getInGame_ProvinceBuild_Visible()) {
                                            CFG.menuManager.setVisible_InGame_ProvinceBuild(false, false);
                                            return true;
                                        }
                                    }
                                    else if (keycode == 46) {
                                        if (CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                            CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
                                            CFG.game.checkProvinceActionMenu();
                                            return true;
                                        }
                                    }
                                    else if (keycode == 48 && CFG.regroupArmyMode) {
                                        CFG.game.resetRegroupArmyData();
                                        CFG.game.checkProvinceActionMenu();
                                        return true;
                                    }
                                }
                                if (CFG.menuManager.getVisible_InGame_ProvinceAction()) {
                                    if (keycode == 45) {
                                        if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
                                            if (!CFG.chooseProvinceMode) {
                                                Menu_InGame_ProvinceAction.clickMove();
                                            }
                                            else {
                                                CFG.game.resetChooseProvinceData();
                                                CFG.game.checkProvinceActionMenu();
                                            }
                                        }
                                    }
                                    else if (keycode == 51) {
                                        if (Menu_InGame_ProvinceAction.canRecruit()) {
                                            Menu_InGame_ProvinceAction.clickRecruit();
                                        }
                                    }
                                    else if (keycode == 33) {
                                        if (Menu_InGame_ProvinceAction.canRecruit()) {
                                            Menu_InGame_ProvinceAction.clickBuild();
                                        }
                                    }
                                    else if (keycode == 46) {
                                        Menu_InGame_ProvinceAction.clickDisband();
                                    }
                                    else if (keycode == 48) {
                                        Menu_InGame_ProvinceAction.clickMoveTo();
                                    }
                                }
                            }
                        }
                    }
                    else if (CFG.menuManager.getInNextPlayerTurn()) {
                        if (keycode == 62) {
                            Menu_NextPlayerTurn.clickBack();
                        }
                    }
                    else if (keycode == 67) {
                        CFG.menuManager.onBackPressed();
                    }
                    else if (CFG.menuManager.getInGame_Timeline() || CFG.menuManager.getInVictory()) {
                        if (keycode == 69) {
                            CFG.timelapseManager.updateSpeed(-1);
                        }
                        else if (keycode == 81) {
                            CFG.timelapseManager.updateSpeed(1);
                        }
                        else if (keycode == 66) {
                            CFG.timelapseManager.pauseUnpause();
                        }
                    }
                    else if (CFG.menuManager.getInGame_CreateAVassal()) {
                        CFG.menuManager.setViewID(Menu.eINGAME);
                    }
                    else if (CFG.menuManager.getInCreateNewGame()) {
                        if (keycode == 244) {
                            Menu_CreateNewGame.clickOptions();
                        }
                        else if (keycode == 61) {
                            Menu_CreateNewGame_Top.clickChooseScenario();
                        }
                    }
                    else if (CFG.menuManager.getInChooseScenario()) {
                        if (keycode == 66 || keycode == 62) {
                            Menu_ChooseScenario_Title.clickLoadScenario();
                        }
                        else if (keycode == 61) {
                            CFG.menuManager.setViewID(Menu.eCREATE_NEW_GAME);
                        }
                        else if (keycode == 20 || keycode == 22) {
                            ++Menu_ChooseScenario_Title.iPreviewScenarioID;
                            final int iPreviewScenarioID = Menu_ChooseScenario_Title.iPreviewScenarioID;
                            CFG.game.getGameScenarios();
                            if (iPreviewScenarioID >= Game_Scenarios.SCENARIOS_SIZE - 1) {
                                Menu_ChooseScenario_Title.iPreviewScenarioID = 0;
                            }
                            Menu_ChooseScenario_Title.loadPreview();
                        }
                        else if (keycode == 19 || keycode == 21) {
                            --Menu_ChooseScenario_Title.iPreviewScenarioID;
                            if (Menu_ChooseScenario_Title.iPreviewScenarioID < 0) {
                                CFG.game.getGameScenarios();
                                Menu_ChooseScenario_Title.iPreviewScenarioID = Game_Scenarios.SCENARIOS_SIZE - 1;
                            }
                            Menu_ChooseScenario_Title.loadPreview();
                        }
                    }
                }
            }
            else if (keycode == 66) {
                CFG.keyboardSave.action();
                CFG.menuManager.getKeyboard().onMenuPressed();
                CFG.menuManager.getKeyboard().setVisible(false);
                Keyboard.activeColor_RGB_ID = -1;
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return false;
    }
    
    @Override
    public boolean keyTyped(final char character) {
        CFG.setRender_3(true);
        try {
            if (CFG.menuManager.getKeyboard().getVisible() && character > '\0') {
                Gdx.app.log("AoC", "" + (int)character + " character: " + character);
                if (character == '' || character == '\b') {
                    CFG.keyboardDelete.action();
                    CFG.menuManager.getKeyboard().onMenuPressed();
                }
                else if (character == 22) {
                    Gdx.app.log("AoC", "Pasted from clipboard: " + Gdx.app.getClipboard().getContents());
                    CFG.keyboardWrite.action("" + Gdx.app.getClipboard().getContents());
                    CFG.menuManager.getKeyboard().onMenuPressed();
                    CFG.toast.setInView(CFG.langManager.get("PastedFromClipboard") + "!");
                }
                else if (character == 3) {
                    Gdx.app.log("AoC", "Copied from clipboard: " + CFG.keyboardMessage);
                    Gdx.app.getClipboard().setContents(CFG.keyboardMessage);
                    CFG.toast.setInView(CFG.langManager.get("CopiedToClipboard") + "!");
                }
                else if (character == 24) {
                    Gdx.app.log("AoC", "Cut from clipboard: " + CFG.keyboardMessage);
                    Gdx.app.getClipboard().setContents(CFG.keyboardMessage);
                    CFG.keyboardMessage = "";
                    CFG.keyboardDelete.action();
                    CFG.menuManager.getKeyboard().onMenuPressed();
                    CFG.toast.setInView(CFG.langManager.get("CutToClipboard") + "!");
                }
                else if (character == 127) {
                    Gdx.app.log("AoC", "Deleted all");
                    CFG.keyboardMessage = "";
                    CFG.keyboardDelete.action();
                    CFG.menuManager.getKeyboard().onMenuPressed();
                    CFG.toast.setInView(CFG.langManager.get("DeletedAll") + "!");
                }
                else if (character != '\r' && character != ';' && character != '<') {
                    CFG.keyboardWrite.action("" + character);
                    CFG.menuManager.getKeyboard().onMenuPressed();
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        try {
            CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK, SoundsManager.PERC_VOLUME_KEYBOARD);
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return false;
    }
    
    protected float getScrolled_ScaleUpdate() {
        if (CFG.map.getMapScale().getCurrentScale() > 1.0f) {
            return 0.1f;
        }
        if (CFG.map.getMapScale().getCurrentScale() >= 0.65f) {
            return 0.05f;
        }
        if (CFG.map.getMapScale().getCurrentScale() >= 0.4f) {
            return 0.02f;
        }
        return 0.01f;
    }
    
    @Override
    public boolean scrolled(final int amount) {
        try {
            if (CFG.menuManager.getIsScrollableY_MenuHovered()) {
                this.updateScroll();
                CFG.menuManager.scrollHoveredMenu_Y((amount == 1) ? (-this.iScroll) : this.iScroll);
            }
            else if (CFG.menuManager.getIsScrollableX_MenuHovered()) {
                this.updateScroll();
                CFG.menuManager.scrollHoveredMenu_X((amount == 1) ? (-this.iScroll) : this.iScroll);
            }
            else if (CFG.menuManager.getIsScrollable_Hovered_MenuElement()) {
                this.updateScroll();
                CFG.menuManager.scrollHoveredMenuElement((amount == 1) ? (-this.iScroll) : this.iScroll);
            }
            else if (amount == 1) {
                CFG.map.getMapScale().setNewCurrentScaleByButton2(-this.getScrolled_ScaleUpdate());
            }
            else if (amount == -1) {
                CFG.map.getMapScale().setNewCurrentScaleByButton2(this.getScrolled_ScaleUpdate());
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        try {
            CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK, SoundsManager.PERC_VOLUME_KEYBOARD);
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        catch (final StackOverflowError ex3) {
            CFG.exceptionStack(ex3);
        }
        catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex4);
        }
        return true;
    }
    
    private final void updateScroll() {
        if (this.lScrollTime + 50L > System.currentTimeMillis()) {
            this.lScrollTime = System.currentTimeMillis();
            this.iScroll += (int)(this.iScroll * 1.2f);
            if (this.iScroll > 75) {
                this.iScroll = 75;
            }
        }
        else {
            this.lScrollTime = System.currentTimeMillis();
            this.iScroll = 15;
        }
    }
    
    @Override
    public void resume() {
        CFG.updateRender(true);
        this.updateRequestRendering(true);
        Gdx.graphics.requestRendering();
        CFG.setRender_3(true);
        super.resume();
    }
    
    @Override
    public void pause() {
        this.updateRequestRendering(false);
        if (!CFG.menuManager.getInLoadMap() && !CFG.menuManager.getInLoadSave() && !CFG.menuManager.getInInitMenu()) {
            CFG.updateRender(false);
        }
        CFG.setRender_3(true);
        super.pause();
    }
    
    static {
        AoCGame.TOP = 0;
        AoCGame.BOTTOM = 0;
        AoCGame.LEFT = 0;
        AoCGame.RIGHT = 0;
        AoCGame.drawFPS = false;
        AoCGame.FPS_LIMIT = 60;
        AoCGame.TYPE_NUMER_TIME = 0L;
        AoCGame.TYPE_NUMBER = 0;
    }
    
    interface RequestRendering
    {
        void update();
    }
}
