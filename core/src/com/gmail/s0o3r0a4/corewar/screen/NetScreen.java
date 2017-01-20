package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;
import com.gmail.s0o3r0a4.corewar.net.Net;
import com.gmail.s0o3r0a4.corewar.net.node.Node;
import com.gmail.s0o3r0a4.corewar.net.node.NodeAddr;

import java.util.ArrayList;
import java.util.Hashtable;

import static com.badlogic.gdx.Gdx.input;

public class NetScreen implements Screen {
    private CoreWar coreWar;

    private ArrayList<Node> nodes;

    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Stage stage;

    private Assets assets;

    private Texture blockTexture;
    private TextureRegion blockTextureRegion;
    private TextureRegionDrawable blockTexRegionDrawable;

    private TextureRegion FABTextureRegion;
    private TextureRegionDrawable FABTexRegionDrawable;
    private ImageButton FAButton;

    private float screenWidth;
    private float screenHeight;

    private Net testNet;
    private ArrayList<ImageButton> testButton;
    private float testButtonX[];
    private float testButtonY[];
//    private ImageButton testButton2;
//    private ImageButton testButton3;
//    private Sprite testButton;

    private boolean changingNode = false;
    private boolean dragged = false;
    private boolean testOnce = false;

    //    private FileHandle files[];
    private Preferences prefs;
    private Hashtable<String, String> allNodes;
    int nodesSize;

    public NetScreen(CoreWar game, Assets assets) {
        this.coreWar = game;
        this.stage = new Stage();
        this.assets = assets;
//            files.add(Gdx.files.local("save/"+nodes.get(i).getAddr().toString()+".json"));
//        files = Gdx.files.local("save/").list();
        this.prefs = Gdx.app.getPreferences("save_0");
        this.nodesSize = prefs.getInteger("totalNumberOfNodes", 2);
        this.nodes = new ArrayList<Node>();
//        Gdx.files.local("save/save.json");
    }

    @Override
    public void show() {
        // README: Get screen size
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        // TODO: Load sound effect, music, and images

        // README: Create camera
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.setToOrtho(false, screenWidth, screenHeight);
        // README: Create viewport
        fitViewport = new FitViewport(screenWidth, screenHeight, camera);
        // README: Get assets
        Skin skin = assets.manager.get(Assets.uiSkin);
        blockTexture = assets.manager.get(Assets.blockTexture);

        blockTextureRegion = new TextureRegion(blockTexture);
        blockTextureRegion.setRegionWidth(30);
        blockTextureRegion.setRegionHeight(30);
        blockTexRegionDrawable = new TextureRegionDrawable(blockTextureRegion);

        // TODO: Test Net
        testNet = new Net(screenWidth, screenHeight);
        testNet.update();
        // TODO: Add visible true after creating the table
        Table testTable = new Table();
        testTable.setFillParent(true);

        testButtonX = new float[3];
        testButtonY = new float[3];
        testButton = new ArrayList<ImageButton>();
        for (int i = 0; i < 3; i++) {
//            testButtonX[i] = (float)testNet.getNodeX(i);
//            testButtonY[i] = (float)testNet.getNodeY(i);
            testButton.add(new ImageButton(blockTexRegionDrawable));
            testButton.get(i).setVisible(false);
            testTable.add(testButton.get(i)).expand().bottom().left();
        }

        // TODO: Default values
        boolean firstTime = prefs.getBoolean("firstTime", true);
        int nodesSize = prefs.getInteger("totalNumberOfNodes", 2);
        if (firstTime) {
            prefs.putBoolean("firstTime", false);
            for (int i = 0; i < nodesSize; i++) {
                NodeAddr nodeAddr = new NodeAddr(2, i);
                nodes.add(new Node(8000, nodeAddr));
            }

            for (int i = 0; i < nodesSize; i++) {
                Hashtable<String, String> hashTable = new Hashtable<String, String>();

                Json json = new Json(JsonWriter.OutputType.minimal);

//            Base64Coder.encodeString(json.toJson(nodeWarGame));

                hashTable.put(nodes.get(i).getAddr().toString(), Base64Coder.encodeString(json.toJson(nodes.get(i))));
                prefs.put(hashTable);
            }

            String IPList = "2.0, 2.1";

            prefs.putString("IPList", IPList);
        }

        Json json = new Json(JsonWriter.OutputType.minimal);

//                    String nodeData = prefs.getString(Integer.toString(i) + "." + Integer.toString(j), null);

//        String IPList = "2.0, 2.1";

//        prefs.putString("IPList", IPList);

        String IPList = prefs.getString("IPList");

        String[] IPs = IPList.split(", ");

        for (int i = 0; i < IPs.length; i++) {
            Node node = json.fromJson(Node.class, Base64Coder.decodeString(prefs.getString(IPs[i])));
            nodes.add(node);
        }

        prefs.flush(); // TODO: Flush for now

        for (int i = 0; i < testButton.size(); i++) {
            final int index = i;
            testButton.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
//                    changingNode = true;
                    dragged = false;
                    return true;
                }

                public void touchUp(InputEvent event, float x, float y,
                                    int pointer, int button) {
                    changingNode = false;
                    if (dragged) {
                        testNet.initialize();
                        dragged = false;
                    } else {
//                        coreWar.setScreen(new GameScreen(coreWar, assets, new CoreDebug(8000)));
                        Json json = new Json(JsonWriter.OutputType.minimal);
//                        nodes.set() = json.fromJson(Node.class, Base64Coder.decodeString(files.get(index).readString()));
//                        Base64Coder.decodeString(prefs.getString(nodes.get(index).getAddr().toString(), );

                        Gdx.app.debug("Open node:", Integer.toString(index));
                        Gdx.app.debug("Open IP:", nodes.get(index).getAddr().toString());
                        Gdx.app.debug("Open IP:", nodes.get(index).getAddr().toString());
                        Node node = json.fromJson(Node.class, Base64Coder.decodeString(prefs.getString(nodes.get(index).getAddr().toString())));
                        coreWar.setScreen(new GameScreen(coreWar, assets, node));
                    }
                }
            });

            testButton.get(i).addListener(new DragListener() {
                public void drag(InputEvent event, float x, float y, int pointer) {
                    dragged = true;
//                    changingNode = true;
//                    testNet.setLocation(index, x - testButton.get(index).getWidth() / 2, y - testButton.get(index).getHeight() / 2);
                    testButton.get(index).moveBy(x - testButton.get(index).getWidth() / 2, y - testButton.get(index).getHeight() / 2);
//                    testButtonX[index] = x - testButton.get(index).getWidth() / 2 /*+ testButtonX[index]*/;
//                    testButtonY[index] = y - testButton.get(index).getHeight() / 2/* + testButtonY[index]*/;
//                    testButton.get(index).setPosition(testButtonX[index], testButtonY[index]);
//                    testNet.setLocation(index, testButton.get(index).getX(), testButton.get(index).getY());

                    Gdx.app.debug(Float.toString(testButtonX[index]), "!");
                    Gdx.app.debug(Float.toString(testButtonY[index]), "?");
                }
            });
        }

        // README: Create an overlay table for the stack
        final Table underLayTable = new Table();
        underLayTable.setFillParent(true);
        underLayTable.add(testTable).expand().bottom().left();
        underLayTable.row();

        // README: Add a Floating Action Button
        FABTextureRegion = new TextureRegion(blockTexture);
        FABTexRegionDrawable = new TextureRegionDrawable(FABTextureRegion);
        FAButton = new ImageButton(blockTexRegionDrawable);
        FAButton.bottom().right();
        FAButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("fucked up", "oh shit");
            }
        });

        // README: Add the FAButton to the FABContainer
        Table FABContainer = new Table();
        FABContainer.setFillParent(true);
        FABContainer.add(FAButton).expand().bottom().right().pad(20); // TODO: BUG cleared

        // README: Stack up the content and FAB
        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(underLayTable);

        // README: Add stack to the stage
        this.stage.addActor(stack);
        input.setInputProcessor(this.stage);

        stack.add(FABContainer);

        stage.setViewport(fitViewport);
        stage.act();
        stage.draw();

        for (int i = 0; i < testButton.size(); i++) {
            testButton.get(i).setVisible(true);
//            testButton.get(i).moveBy((float)testNet.getNodeX(i), (float)testNet.getNodeY(i));
        }

        // TODO: Put all startup debug information here
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("Net Screen", "Created");
        Gdx.app.debug("Stage width", Float.toString(stage.getWidth()));
        Gdx.app.debug("Stage height", Float.toString(stage.getHeight()));
//        Gdx.app.debug(Float.toString((float)testNet.x), Float.toString((float)testNet.y));
//        Gdx.app.debug(Float.toString((float)testNet.eneX), Float.toString((float)testNet.eneY));
    }

    @Override
    public void render(float delta) {
        // clear the screen with a black color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();


        camera.setToOrtho(false, screenWidth, screenHeight);

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        coreWar.batch.setProjectionMatrix(camera.combined);
//        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        coreWar.batch.begin();

//        coreWar.batch.draw(testButton, );

//        for (int i = 0; i < testNet.getPortsSize(); i++)
//        {
//            Port port = testNet.getPort(i);
//            int j = port.getNode1ID();
//            int k = port.getNode2ID();
//
//            testNet.update();
//            drawLine(testButtonX[j], testButtonY[j],
//                    testButtonX[k], testButtonY[k], 1f);
//            drawLine(testButton.get(j).getX() + testButton.get(j).getWidth() / 2, testButton.get(j).getY() + testButton.get(j).getHeight() / 2,
//                    testButton.get(k).getX() + testButton.get(k).getWidth() / 2, testButton.get(k).getY() + testButton.get(k).getHeight() / 2, 1f);
//
//            drawLine(0f, 0f, screenWidth, screenHeight, 1.0f);
//
//            Gdx.app.debug(Float.toString((float)testNet.x), Float.toString((float)testNet.y));
//        }

        coreWar.batch.end();

        stage.setViewport(fitViewport);
        stage.act();
        stage.draw();

        // TODO: Test Net
        for (int i = 0; i < testButton.size(); i++) {
            if (!changingNode) {
                testNet.update();
//                testButton.get(i).setPosition((float)testNet.getNodeX(i), (float)testNet.getNodeY(i));
//                testButton.get(i).setPosition(testButtonX[i], testButtonY[i]);
            }
        }

        // README: Get input
//        if ((Gdx.input.isKeyPressed(Input.Keys.BACK)))
//        {
//            Gdx.app.debug("hi", "auntie");
//            coreWar.setScreen(new MainMenuScreen(coreWar, assets));
//        }

    }


    @Override
    public void resize(int width, int height) {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();

//        fitViewport.setCamera(camera);
        fitViewport.update(width, height, true);
        coreWar.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        prefs = Gdx.app.getPreferences("save_0");

        for (int i = 0; i < nodesSize; i++) {
            Hashtable<String, String> hashTable = new Hashtable<String, String>();

            Json json = new Json(JsonWriter.OutputType.minimal);

//            Base64Coder.encodeString(json.toJson(nodeWarGame));

            hashTable.put(nodes.get(i).getAddr().toString(), Base64Coder.encodeString(json.toJson(nodes.get(i))));
            prefs.put(hashTable);
        }

        prefs.flush();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    //    void drawLine(float x1, float y1, int x2, int y2, int thickness) {
//    float dx = x2-x1;
//    float dy = y2-y1;
//    float dist = (float)Math.sqrt(dx*dx + dy*dy);
//    float rad = (float)Math.atan2(dy, dx);
//    coreWar.batch.draw(blockTexture, x1, y1, dist, thickness, 0, 0, rad);
//}
    private void drawLine(float x1, float y1, float x2, float y2, float thickness) {
        Vector2 vector1 = new Vector2(x1, y1);
        Vector2 vector2 = new Vector2(x2, y2);
        float distance = vector1.dst(vector2);
//        float angle = vector1.angle(vector2);
        float angle = (float) Math.toDegrees(Math.atan2(y1 - y2, x1 - x2));
        if (angle < 0) {
            angle += 360;
        }
        angle += 90; // README: Different 0 degree direction, LibGdx starts from 12 o'clock
        coreWar.batch.setColor(Color.GREEN);
        coreWar.batch.draw(blockTextureRegion, x1, y1, 0, 0, thickness, distance, 1, 1, angle, true);
    }

}