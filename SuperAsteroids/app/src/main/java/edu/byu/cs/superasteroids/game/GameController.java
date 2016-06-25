package edu.byu.cs.superasteroids.game;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;
import edu.byu.cs.superasteroids.androids_game_definitions.GrowingAsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelAsteroidsType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelObjectsType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelType;
import edu.byu.cs.superasteroids.androids_game_definitions.OcteroidAsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.RegularAsteroidType;
import edu.byu.cs.superasteroids.androids_game_objects.Asteroid;
import edu.byu.cs.superasteroids.androids_game_objects.BgObject;
import edu.byu.cs.superasteroids.androids_game_objects.GrowingAsteroid;
import edu.byu.cs.superasteroids.androids_game_objects.OcteroidAsteroid;
import edu.byu.cs.superasteroids.androids_game_objects.Projectile;
import edu.byu.cs.superasteroids.androids_game_objects.RegularAsteroid;
import edu.byu.cs.superasteroids.androids_game_objects.ScreenRect;
import edu.byu.cs.superasteroids.androids_game_objects.Ship;
import edu.byu.cs.superasteroids.androids_game_objects.WorldRect;
import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.AudioManagement;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.AsteroidsProgram;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * Created by seanwade on 2/25/16.
 */
public class GameController implements IGameDelegate{

    //loadContent intializes the biz
    //iterate through every visibleObject for draw and update.
    //Make a viewport object, subset of modelClass. onty thing in there is a rectange, width, height(matching size of screen
    // inherits from android rectangle

    //positionObject has a boundingRectangle

    private GameActivity gameView;
    private AsteroidsProgram asteroidsProgram = AsteroidsProgram.getInstance();
    private Ship ship;
    private AssetManager am;
    private ContentManager cm;
    private int currentLevelNumber;
    private boolean newLevel;
    private Rect miniMapRect;
    private boolean firstDrawCall;
    private int titleDraw;


    public GameController(GameActivity gameView) {

        this.gameView = gameView;
        this.am = gameView.getAssets();
        this.cm = ContentManager.getInstance();
        this.ship = asteroidsProgram.getShip();
        this.newLevel = true;
        titleDraw = 0;
    }

    @Override
    public void update(double elapsedTime) {
        if (titleDraw < 60) {
            titleDraw++;
            return;
        }
        // Check if the game is over.
        try {
            if (asteroidsProgram.getCurAsteroidList().isEmpty()) {
                titleDraw = 0;
                loadLevel(currentLevelNumber);
                currentLevelNumber++;
                newLevel = true;
            }
            if (ship.getHealth() <= 0) {
                throw new Exception("All lives lost");
            }
        }
        catch (Exception e) {

                DrawingHelper.drawText(new Point(Math.round(DrawingHelper.getGameViewWidth() / 2),
                        Math.round(DrawingHelper.getGameViewHeight() * 3 / 4)), "GAME OVER!", Color.RED, 200);
        }

        // Place ship in the center of the screen.
//        Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth() / 2,
//                DrawingHelper.getGameViewHeight() / 2);
//        asteroidsProgram.getShip().setPositionX(gameViewCenter.x);
//        asteroidsProgram.getShip().setPositionY(gameViewCenter.y);

        // Deal with input
        if (InputManager.movePoint != null) {
            PointF distance = GraphicsUtils.subtract(WorldRect.convertWorldToScreen(ship.getCenterPoint()), InputManager.movePoint);
            double angle = calcTouchAngle(distance);
            asteroidsProgram.getShip().setRotation(angle);
            asteroidsProgram.getShip().setSpeed(400);
            asteroidsProgram.getShip().setDirection((float) angle);
            ship.setSpeed(ship.getEngine().getEngineType().getBaseSpeed());
            asteroidsProgram.getShip().update(elapsedTime);
            InputManager.movePoint = null;
        }
        if (InputManager.firePressed == true) {
            float cannonBoost = ship.getPowerCore().getPowerCoreType().getCannonBoost();
            double rotation = ship.getRotation() + 3 * Math.PI / 2;
            Projectile newProj = new Projectile((int) ship.getEmitPoint().x, (int) ship.getEmitPoint().y, rotation, 900, (float) rotation, -1, ship.getCannon().getCannonType(),
                    ContentManager.getInstance().getImageId(ship.getCannon().getCannonType().getAttackImage()));
            //newProj.setRect(new RectF(ship.getEmitPoint().x, 0, ship.getCannon().getCannonType().getAttackImageWidth(), ship.getCannon().getCannonType().getAttackImageHeight()));
            asteroidsProgram.getCurProjectileList().add(newProj);
            AudioManagement.getInstance().playSound(asteroidsProgram.getAttackSounds().get(0), (float) .8, 1);
        }

        // Update Projectiles
        for (Projectile projectile : asteroidsProgram.getCurProjectileList()) {
            projectile.update(elapsedTime);
        }

        //Update Asteroids
        for (Asteroid asteroid : asteroidsProgram.getCurAsteroidList()) {
            if (Math.round(asteroid.getRect().left) <=  Math.round(WorldRect.rect.left) || Math.round(asteroid.getRect().top) <= Math.round(WorldRect.rect.top)
                    || Math.round(asteroid.getRect().bottom) >= Math.round(WorldRect.rect.bottom) || Math.round(asteroid.getRect().right) >= Math.round(WorldRect.rect.right)) {
                GraphicsUtils.RicochetObjectResult result =
                        GraphicsUtils.ricochetObject(asteroid.getCenterPoint(), asteroid.getRect(),
                                asteroid.getDirection(), WorldRect.width, WorldRect.height);
                asteroid.setDirection((float) result.getNewAngleRadians());
                asteroid.setRect(result.getNewObjBounds());
                asteroid.setCenterPoint(result.getNewObjPosition());
            }
            asteroid.update(elapsedTime);
        }

        // check asteroid hit
        List<Asteroid> tempAsteroids = new ArrayList<>();
        List<Projectile> tempProjectiles = new ArrayList<>();

        for (Asteroid asteroid: asteroidsProgram.getCurAsteroidList()) {
            for (Projectile projectile : asteroidsProgram.getCurProjectileList()) {
                if (asteroid.getRect().intersect(projectile.getRect())) {
                    tempAsteroids.add(asteroid);
                    tempProjectiles.add(projectile);
                }
            }
        }
        for (Asteroid asteroid: tempAsteroids) {
            asteroidsProgram.getCurAsteroidList().remove(asteroid);
            //Regular
            if (asteroid.getImageId() == cm.getImageId("images/asteroids/asteroid.png") && asteroid.isHit() == false) {
                GrowingAsteroid newG = new GrowingAsteroid(asteroid.getPositionX(),
                        asteroid.getPositionY(), 0, asteroid.getSpeed(), (float) ( Math.PI/2), -1, 1,
                        (GrowingAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(1), asteroid.getImageId());
                newG.setXscale(GC.XSCALE);
                newG.setYscale(GC.YSCALE);
                newG.setHit(true);
                asteroidsProgram.getCurAsteroidList().add(newG);
                GrowingAsteroid newG2 = new GrowingAsteroid(asteroid.getPositionX(),
                        asteroid.getPositionY(), 0, asteroid.getSpeed(), (float) ( -1 * Math.PI/2), -1, 1,
                        (GrowingAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(1), asteroid.getImageId());
                newG2.setXscale(GC.XSCALE);
                newG2.setYscale(GC.YSCALE);
                newG2.setHit(true);
                asteroidsProgram.getCurAsteroidList().add(newG2);
            }

            if (asteroid.getImageId() == cm.getImageId("images/asteroids/trump.png") && asteroid.isHit() == false) {
                GrowingAsteroid newG = new GrowingAsteroid(asteroid.getPositionX(),
                        asteroid.getPositionY(), 0, asteroid.getSpeed(), (float) ( Math.PI/2), -1, 1,
                        (GrowingAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(1), asteroid.getImageId());
                newG.setXscale(GC.XSCALE);
                newG.setYscale(GC.YSCALE);
                newG.setHit(true);
                asteroidsProgram.getCurAsteroidList().add(newG);
                GrowingAsteroid newG2 = new GrowingAsteroid(asteroid.getPositionX(),
                        asteroid.getPositionY(), 0, asteroid.getSpeed(), (float) ( -1 * Math.PI/2), -1, 1,
                        (GrowingAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(1), asteroid.getImageId());
                newG2.setXscale(GC.XSCALE);
                newG2.setYscale(GC.YSCALE);
                newG2.setHit(true);
                asteroidsProgram.getCurAsteroidList().add(newG2);
            }

            asteroidsProgram.getCurAsteroidList().remove(asteroid);
        }
        tempAsteroids.clear();
        for (Projectile projectile: tempProjectiles) {
            asteroidsProgram.getCurProjectileList().remove(projectile);
        }
        tempProjectiles.clear();
        for (Asteroid asteroid: asteroidsProgram.getCurAsteroidList()) {
            if (asteroid.getRect().intersect(ship.getRect())) {
                ship.setHealth(ship.getHealth() - 1);
                DrawingHelper.drawFilledCircle(ship.getCenterPoint(), (ship.getRect().right - ship.getRect().left), Color.RED, 100);
                tempAsteroids.add(asteroid);
            }
        }
        for (Asteroid asteroid: tempAsteroids) {
            asteroidsProgram.getCurAsteroidList().remove(asteroid);
        }
        tempAsteroids.clear();

        for (Asteroid asteroid : asteroidsProgram.getCurAsteroidList()) {
            if (asteroid.getImageId() == cm.getImageId("images/asteroids/blueasteroid.png") && asteroid.getXscale() < 5) {
                asteroid.setXscale(asteroid.getXscale() * 1.001);
                asteroid.setYscale(asteroid.getYscale() * 1.001);
            }
            if (asteroid.getImageId() == cm.getImageId("images/asteroids/trump.png") && asteroid.getXscale() < 5) {
                asteroid.setXscale(asteroid.getXscale() * 1.001);
                asteroid.setYscale(asteroid.getYscale() * 1.001);
            }
        }
    }


    @Override
    public void loadContent(ContentManager content) {
        asteroidsProgram.getCurProjectileList().clear();
        asteroidsProgram.getCurAsteroidList().clear();
        asteroidsProgram.loadContent(ContentManager.getInstance());
        currentLevelNumber = 1;
        newLevel = true;
        loadLevel(currentLevelNumber);
    }

    @Override
    public void unloadContent(ContentManager content) {
        asteroidsProgram.unloadContent();
    }

    @Override
    public void draw() {
        if (titleDraw < 60 && newLevel == true) {
            DrawingHelper.drawFilledRectangle(
                    new Rect(0, 0, 3000, 3000), Color.GREEN, 220);
            try {
                DrawingHelper.drawText(new Point(Math.round(DrawingHelper.getGameViewWidth() / 3),
                                Math.round(DrawingHelper.getGameViewHeight() * 3 / 2)),
                        "Hint: " + asteroidsProgram.getLevelTypeMap().get(currentLevelNumber).getHint(), Color.BLUE, 255);
                DrawingHelper.drawText(new Point(Math.round(DrawingHelper.getGameViewWidth() / 3),
                                Math.round(DrawingHelper.getGameViewHeight() / 2)),
                        "Level " + Integer.toString(currentLevelNumber), Color.BLUE, 255);
            }
            catch (Exception e) {
                for (;;) {

                }
            }


            Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth() / 2,
                DrawingHelper.getGameViewHeight() / 2);
            asteroidsProgram.getShip().setPositionX(gameViewCenter.x);
            asteroidsProgram.getShip().setPositionY(gameViewCenter.y);
            return;
        }
        if (firstDrawCall == true) {
            try {
                ScreenRect.height = DrawingHelper.getGameViewHeight();
                ScreenRect.width = DrawingHelper.getGameViewWidth();
                ScreenRect.rect = new RectF(0, 0, ScreenRect.width, ScreenRect.height);

                WorldRect.height = asteroidsProgram.getLevelTypeMap().get(currentLevelNumber).getHeight();
                WorldRect.width = asteroidsProgram.getLevelTypeMap().get(currentLevelNumber).getWidth();
                WorldRect.rect = new RectF(0, 0, WorldRect.width, WorldRect.height);

                ship.setCenterPoint(new PointF(Math.round(DrawingHelper.getGameViewWidth() / 2), Math.round(DrawingHelper.getGameViewHeight() / 2)));
                firstDrawCall = false;
            }
            catch (Exception e) {}
        }

        // Draw the stars Background
        DrawingHelper.drawImage(cm.loadImage("images/space.bmp"), 0, 0, 0, (float) 3, (float) 1.5, GC.OPAQUE);

        //Draw all the background images
        for (BgObject bg : asteroidsProgram.getCurBgObjectList()) {
            bg.draw();
        }

        // Draw the ship
        asteroidsProgram.getShip().draw();
        //RectF re = asteroidsProgram.getShip().getRect();
        //DrawingHelper.drawRectangle(new Rect(Math.round(re.left), Math.round(re.top),
        // Math.round(re.right), Math.round(re.bottom)), 122, 255);

        // Draw asteroids
        for (Asteroid asteroid : asteroidsProgram.getCurAsteroidList()) {
            asteroid.draw();
//            f
        }
        // Draw Projectiles
        for (Projectile projectile : asteroidsProgram.getCurProjectileList()) {
            projectile.draw();
            //RectF r = projectile.getRect();
            //DrawingHelper.drawRectangle(new Rect(Math.round(r.left),
            // Math.round(r.top), Math.round(r.right), Math.round(r.bottom)), 122, 255);
        }

        //-- MiniMap -------------------------------------------------------------------------
        int left = (int) Math.round(DrawingHelper.getGameViewWidth() * .8);
        int top = (int) Math.round(DrawingHelper.getGameViewHeight() * .02);
        int right = (int) Math.round(DrawingHelper.getGameViewWidth() * .98);
        int bottom = (int) Math.round(DrawingHelper.getGameViewHeight() * .2);
        miniMapRect = new Rect(left, top, right, bottom);
        DrawingHelper.drawFilledRectangle(miniMapRect, Color.GREEN, 130);
        DrawingHelper.drawRectangle(miniMapRect, Color.BLUE, 160);

        // Asteroid Position dots
        for (Asteroid asteroid : asteroidsProgram.getCurAsteroidList()) {
            double xScale = ((double) right - left) / (double) WorldRect.width;
            double yScale = ((double) bottom - top) / (double) WorldRect.height;
            double scaledX = asteroid.getCenterPoint().x * xScale;
            double scaledY = asteroid.getCenterPoint().y * yScale;
            PointF pointPoint = new PointF(Math.round(left + scaledX), Math.round(top + scaledY));
            DrawingHelper.drawPoint(pointPoint, 8, Color.RED, 255);
        }

        // for ship dot
        double xScale = ((double) right - left) / (double) WorldRect.width;
        double yScale = ((double) bottom - top) / (double) WorldRect.height;
        double scaledX = ship.getCenterPoint().x * xScale;
        double scaledY = ship.getCenterPoint().y * yScale;
        PointF pointPoint = new PointF(Math.round(left + scaledX), Math.round(top + scaledY));
        DrawingHelper.drawPoint(pointPoint, 10, Color.BLUE, 255);
        //-------------------------------------------------------------------------------------
            DrawingHelper.drawText(new Point(30, 85), "Lives: " + Integer.toString(ship.getHealth()), Color.RED, 65);


        // New Level Transition
//        if (newLevel == true) {
//            DrawingHelper.drawText(new Point(Math.round(DrawingHelper.getGameViewWidth() / 2),
//                    Math.round(DrawingHelper.getGameViewHeight() / 2)),
//                    "Level " + Integer.toString(currentLevelNumber - 1), 155, 200);
//            // FIXME make the time delay work.
////            try {
////                Thread.sleep(1000);
////            } catch (Exception e) {
////
////            }
//            newLevel = false;
//        }
    }

    public void loadLevel(int levelNumber) {
        LevelType levelType = asteroidsProgram.getLevelTypeMap().get(levelNumber);

        // Starting Dimensions, will be changed once view is loaded
        ScreenRect.height = 2000;
        ScreenRect.width = 2000;
        ScreenRect.leftCorner = new Point(0,0);
        ScreenRect.calcRect();

        WorldRect.height = 2000;
        WorldRect.width = 2000;
        WorldRect.leftCorner = new Point(0,0);
        WorldRect.calcRect();

        firstDrawCall = true;
        AudioManagement audioManagement = AudioManagement.getInstance();
        audioManagement.playLoop(asteroidsProgram.getLevelSounds().get(0));

        // Load In Level Asteroids
        List<Asteroid> curAsteroids = asteroidsProgram.getCurAsteroidList();
        List<LevelAsteroidsType> levelAsteroids = levelType.getLevelAsteroids();
        Random generator = new Random();
        for (LevelAsteroidsType asteroidType : levelAsteroids) {
            for (int i = 0; i < asteroidType.getNumberOfAsteroids(); i++) {
                int xPos = generator.nextInt(WorldRect.width - 1 + 1) + 1;
                int yPos = generator.nextInt(WorldRect.height - 1 + 1) + 1;
                int speed = generator.nextInt(200) + 50;
                double rotation = 0 + (Math.PI * 2 - 0) * generator.nextDouble();
                if (asteroidType.getAsteroidId() == 1) {
                    int imageId = cm.getImageId(asteroidsProgram.getAsteroidTypeSet().get(0).getImagePath());
                    RegularAsteroid newRegular = new RegularAsteroid(xPos, yPos, 0, speed, (float) rotation, 0, 0,
                            (RegularAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(0), imageId);
                    newRegular.setHit(false);
                    newRegular.setXscale(1);
                    newRegular.setYscale(1);
                    curAsteroids.add(newRegular);
                }
                if (asteroidType.getAsteroidId() == 2) {
                    int imageId = cm.getImageId(asteroidsProgram.getAsteroidTypeSet().get(1).getImagePath());
                    GrowingAsteroid newGrowing = new GrowingAsteroid(xPos, yPos, 0, speed, (float) rotation, 0, 0,
                            (GrowingAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(1), imageId);
                    newGrowing.setHit(false);
                    newGrowing.setXscale(1);
                    newGrowing.setYscale(1);
                    curAsteroids.add(newGrowing);
                }
                if (asteroidType.getAsteroidId() == 3) {
                    int imageId = cm.getImageId(asteroidsProgram.getAsteroidTypeSet().get(2).getImagePath());
                    OcteroidAsteroid newOct = new OcteroidAsteroid(xPos, yPos, 0, speed, (float) rotation, 0, 0,
                            (OcteroidAsteroidType) asteroidsProgram.getAsteroidTypeSet().get(2), imageId);
                    newOct.setHit(false);
                    newOct.setXscale(1);
                    newOct.setYscale(1);
                    curAsteroids.add(newOct);
                }
            }
        }

        // Load In Background Objects
        List<LevelObjectsType> levelBgObjects = levelType.getLevelBgObjects();
        List<BgObjectType> bgObjectList = asteroidsProgram.getBgObjectTypeSet();
        for (LevelObjectsType object : levelBgObjects) {
            BgObjectType bgObjectType = bgObjectList.get(object.getObjectId() - 1);//FIXME the minus bizz
            BgObject bgObject = new BgObject(object.getPositionX(), object.getPositionY(), 0, bgObjectType, asteroidsProgram.getBgImages().get(object.getObjectId()));
            bgObject.setCenterPoint(new PointF(object.getPositionX(), object.getPositionY()));
            bgObject.setRotation(0);
            bgObject.setxScale(object.getScale());
            bgObject.setyScale(object.getScale());
            asteroidsProgram.getCurBgObjectList().add(bgObject);
        }
    }

    public RectF calcRect(int width, int height, PointF centerPoint) {
        int left = (int) Math.round(centerPoint.x - .5 * width);
        int right = (int) Math.round(centerPoint.x + .5 * width);
        int top = (int) Math.round(centerPoint.y - .5 * height);
        int bottom = (int) Math.round(centerPoint.x + .5 * height);
        return new RectF(left, top, right, bottom);

    }

    public double calcTouchAngle(PointF point) {
        return Math.atan2(point.x, -1 * point.y) + Math.PI;
    }
}