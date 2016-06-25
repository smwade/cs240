package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.androids_game_definitions.AsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;
import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.androids_game_definitions.EngineType;
import edu.byu.cs.superasteroids.androids_game_definitions.ExtraPartType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelType;
import edu.byu.cs.superasteroids.androids_game_definitions.MainBodyType;
import edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType;
import edu.byu.cs.superasteroids.androids_game_objects.BgObject;
import edu.byu.cs.superasteroids.androids_game_objects.Cannon;
import edu.byu.cs.superasteroids.androids_game_objects.Engine;
import edu.byu.cs.superasteroids.androids_game_objects.ExtraPart;
import edu.byu.cs.superasteroids.androids_game_objects.MainBody;
import edu.byu.cs.superasteroids.androids_game_objects.PowerCore;
import edu.byu.cs.superasteroids.androids_game_objects.Ship;
import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.AsteroidsProgram;

/**
 * This class is Controls the ShipBuilder view and methods.
 */
public class ShipBuildingController implements IShipBuildingController {

    private static final IShipBuildingView.ViewDirection LEFT =
            IShipBuildingView.ViewDirection.LEFT;
    private static final IShipBuildingView.ViewDirection RIGHT =
            IShipBuildingView.ViewDirection.RIGHT;
    private static final IShipBuildingView.ViewDirection UP =
            IShipBuildingView.ViewDirection.UP;
    private static final IShipBuildingView.ViewDirection DOWN =
            IShipBuildingView.ViewDirection.DOWN;
    private static final IShipBuildingView.PartSelectionView CANNON =
            IShipBuildingView.PartSelectionView.CANNON;
    private static final IShipBuildingView.PartSelectionView MAIN_BODY =
            IShipBuildingView.PartSelectionView.MAIN_BODY;
    private static final IShipBuildingView.PartSelectionView ENGINE =
            IShipBuildingView.PartSelectionView.ENGINE;
    private static final IShipBuildingView.PartSelectionView EXTRA_PART =
            IShipBuildingView.PartSelectionView.EXTRA_PART;
    private static final IShipBuildingView.PartSelectionView POWER_CORE =
            IShipBuildingView.PartSelectionView.POWER_CORE;



    private IShipBuildingView controllerView;
    private ContentManager cm;
    private AsteroidsProgram asteroidsProgram;
    private Ship ship;
    private IShipBuildingView.PartSelectionView currentScreen;

    private int mainbodyindex;
    private int cannonIndex;
    private int engineIndex;
    private int extraPartIndex;
    private int powerCoreIndex;


    public ShipBuildingController(IShipBuildingView shipBuildingView) {
        this.controllerView = shipBuildingView;
        setCm(ContentManager.getInstance());
        setAsteroidsProgram(AsteroidsProgram.getInstance());

        this.ship = asteroidsProgram.getShip();
        this.currentScreen = IShipBuildingView.PartSelectionView.MAIN_BODY;

        // Selected Index
        this.mainbodyindex = -1;
        this.cannonIndex = -1;
        this.engineIndex = -1;
        this.extraPartIndex = -1;
        this.powerCoreIndex = -1;
    }



    public void checkStartEnable() {
        if (mainbodyindex != -1 && engineIndex != -1 && extraPartIndex != -1 && powerCoreIndex != -1 && cannonIndex != -1) {
            controllerView.setStartGameButton(true);
        }
        return;
    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        if (partView.equals(MAIN_BODY)) {
            controllerView.setArrow(MAIN_BODY, LEFT, true, "Power Core");
            controllerView.setArrow(MAIN_BODY, RIGHT, true, "Cannon");
            controllerView.setArrow(MAIN_BODY, UP, false, "");
            controllerView.setArrow(MAIN_BODY, DOWN, false, "");
            currentScreen = partView;
            controllerView.setPartViewImageList(currentScreen, asteroidsProgram.getMainBodyImages().subList(0, 2));
            checkStartEnable();
        }
        if (partView.equals(CANNON)) {
            controllerView.setArrow(CANNON, LEFT, true, "Main Body");
            controllerView.setArrow(CANNON, RIGHT, true, "ExtraPart");
            controllerView.setArrow(CANNON, UP, false, "");
            controllerView.setArrow(CANNON, DOWN, false, "");
            currentScreen = partView;
            controllerView.setPartViewImageList(currentScreen, asteroidsProgram.getCannonImages().subList(0, 2));
            checkStartEnable();
        }
        if (partView.equals(IShipBuildingView.PartSelectionView.EXTRA_PART)) {
            controllerView.setArrow(EXTRA_PART, LEFT, true, "Cannon");
            controllerView.setArrow(EXTRA_PART, RIGHT, true, "Engine");
            controllerView.setArrow(EXTRA_PART, UP, false, "");
            controllerView.setArrow(EXTRA_PART, DOWN, false, "");
            currentScreen = partView;
            controllerView.setPartViewImageList(currentScreen, asteroidsProgram.getExtraPartImages().subList(0,2));
            checkStartEnable();
        }
        if (partView.equals(IShipBuildingView.PartSelectionView.ENGINE)) {
            controllerView.setArrow(ENGINE, LEFT, true, "Extra Part");
            controllerView.setArrow(ENGINE, RIGHT, true, "Power Core");
            controllerView.setArrow(ENGINE, UP, false, "");
            controllerView.setArrow(ENGINE, DOWN, false, "");
            currentScreen = partView;
            controllerView.setPartViewImageList(currentScreen, asteroidsProgram.getEngineImages().subList(0,2));
            checkStartEnable();
        }
        if (partView.equals(IShipBuildingView.PartSelectionView.POWER_CORE)) {
            controllerView.setArrow(POWER_CORE, LEFT, true, "Engine");
            controllerView.setArrow(POWER_CORE, RIGHT, true, "Main Body");
            controllerView.setArrow(POWER_CORE, UP, false, "");
            controllerView.setArrow(POWER_CORE, DOWN, false, "");
            currentScreen = partView;
            controllerView.setPartViewImageList(currentScreen, asteroidsProgram.getPowerCoreImages().subList(0,2));
            checkStartEnable();
        }

        Log.d("CurrentView", currentScreen.name());
    }

    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void loadContent(ContentManager content) {
        asteroidsProgram.loadContent(content);
    }

    @Override
    public void unloadContent(ContentManager content) {
        asteroidsProgram.unloadContent();
    }

    @Override
    public void draw() {
        ship.draw();
   }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        if (currentScreen.equals(MAIN_BODY)) {
            if (direction.equals(RIGHT)) {
                controllerView.animateToView(POWER_CORE, LEFT);
            }
            if (direction.equals(LEFT)) {
                controllerView.animateToView(CANNON, RIGHT);
            }
        }
        if (currentScreen.equals(CANNON)) {
            if (direction.equals(RIGHT)) {
                controllerView.animateToView(MAIN_BODY, LEFT);
            }
            if (direction.equals(LEFT)) {
                controllerView.animateToView(EXTRA_PART, RIGHT);
            }
        }
        if (currentScreen.equals(EXTRA_PART)) {
            if (direction.equals(RIGHT)) {
                controllerView.animateToView(CANNON, LEFT);
            }
            if (direction.equals(LEFT)) {
                controllerView.animateToView(ENGINE, RIGHT);
            }
        }
        if (currentScreen.equals(ENGINE)) {
            if (direction.equals(RIGHT)) {
                controllerView.animateToView(EXTRA_PART, LEFT);
            }
            if (direction.equals(LEFT)) {
                controllerView.animateToView(POWER_CORE, RIGHT);
            }
        }
        if (currentScreen.equals(POWER_CORE)) {
            if (direction.equals(RIGHT)) {
                controllerView.animateToView(ENGINE, LEFT);
            }
            if (direction.equals(LEFT)) {
                controllerView.animateToView(MAIN_BODY, RIGHT);
            }
        }
    }

    @Override
    public void onPartSelected(int index) {
        if (currentScreen.equals(MAIN_BODY)) {
            int imageIndex = asteroidsProgram.getMainBodyImages().get(index);
            ship.setMainBody(new MainBody(imageIndex));
            Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth() / 2,
                    DrawingHelper.getGameViewHeight() / 2);
            Log.d("x point", String.valueOf(gameViewCenter.x));
            Log.d("y point", String.valueOf(gameViewCenter.y));
            Log.d("view width", String.valueOf(DrawingHelper.getGameViewWidth()));
            Log.d("view height", String.valueOf(DrawingHelper.getGameViewHeight()));
            ship.setPositionX(gameViewCenter.x);
            ship.setPositionY(gameViewCenter.y);
            ship.getMainBody().setPositionX(gameViewCenter.x);
            ship.getMainBody().setPositionY(gameViewCenter.y);
            ship.getMainBody().setMainBodyType(asteroidsProgram.getMainBodyTypeSet().get(index));
            int imageWidth = ship.getMainBody().getMainBodyType().getImageWidth();
            int imageHeight = ship.getMainBody().getMainBodyType().getImageHeight();
            // ship.getMainBody().setRect(new Rect());
            mainbodyindex = index;
            checkStartEnable();
        }
        if (currentScreen.equals(EXTRA_PART)) {
            int imageIndex = asteroidsProgram.getExtraPartImages().get(index);
            ship.setExtraPart(new ExtraPart(imageIndex));
            ship.getExtraPart().setExtraPartType(asteroidsProgram.getExtraPartTypeSet().get(index));
            extraPartIndex = index;
            ship.update(1/60);
            checkStartEnable();
        }
        if (currentScreen.equals(ENGINE)) {
            int imageIndex = asteroidsProgram.getEngineImages().get(index);
            Engine newEngine = new Engine(imageIndex);
            ship.setEngine(new Engine(imageIndex));
            ship.getEngine().setEngineType(asteroidsProgram.getEngineTypeSet().get(index));
            ship.getEngine().setSpeed(ship.getEngine().getEngineType().getBaseSpeed());
            engineIndex = index;
            ship.update(1/60);
            checkStartEnable();
        }
        if (currentScreen.equals(CANNON)) {
            int imageIndex = asteroidsProgram.getCannonImages().get(index);
            ship.setCannon(new Cannon(imageIndex));
            ship.getCannon().setCannonType(asteroidsProgram.getCannonTypeSet().get(index));
            cannonIndex = index;
            ship.update(1/60);
            checkStartEnable();
        }
        if (currentScreen.equals(POWER_CORE)) {
            int imageIndex = asteroidsProgram.getPowerCoreImages().get(index);
            ship.setPowerCore(new PowerCore(imageIndex));
            ship.getPowerCore().setPowerCoreType(asteroidsProgram.getPowerCoreType().get(index));
            powerCoreIndex = index;
            ship.update(1/60);
            checkStartEnable();
        }
    }

    @Override
    public void onStartGamePressed() {
        controllerView.startGame();
    }

    @Override
    public void onResume() {

    }

    @Override
    public IView getView() {
        return controllerView;
    }

    @Override
    public void setView(IView view) {
    }

    public ContentManager getCm() {
        return cm;
    }

    public void setCm(ContentManager cm) {
        this.cm = cm;
    }

    public AsteroidsProgram getAsteroidsProgram() {
        return asteroidsProgram;
    }

    public void setAsteroidsProgram(AsteroidsProgram asteroidsProgram) {
        this.asteroidsProgram = asteroidsProgram;
    }
}
