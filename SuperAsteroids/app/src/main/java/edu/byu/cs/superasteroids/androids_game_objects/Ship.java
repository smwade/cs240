package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This class is the ship.  It is a collection of parts and involved in the ShipBuilder and the game.
 */
public class Ship extends MovingObject {
    private Cannon cannon;
    private PowerCore powerCore;
    private ExtraPart extraPart;
    private Engine engine;
    private MainBody mainBody;
    private PointF emitPoint;
    private int health;
    private RectF rect;

    public Ship(int positionX, int positionY, int rotation, float speed, float direction,
                float acceleration, Cannon cannon, PowerCore powerCore, ExtraPart extraPart,
                Engine engine, MainBody mainBody, int health, int imageId) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.cannon = cannon;
        this.powerCore = powerCore;
        this.extraPart = extraPart;
        this.engine = engine;
        this.mainBody = mainBody;
        this.health = health;
    }

    //constructor calculates x and y pos
    public Ship(int rotation, float speed, float direction,
                float acceleration, Cannon cannon, PowerCore powerCore, ExtraPart extraPart,
                Engine engine, MainBody mainBody, int health, int imageId) {
        super(mainBody.getPositionX(), mainBody.getPositionY(), rotation, speed, direction, acceleration, imageId);
        this.cannon = cannon;
        this.powerCore = powerCore;
        this.extraPart = extraPart;
        this.engine = engine;
        this.mainBody = mainBody;
        this.health = health;
    }

    public Ship() {
        super();
        this.cannon = null;
        this.powerCore = null;
        this.extraPart = null;
        this.engine = null;
        this.mainBody = null;
        this.health = 5;
    }

    @Override
    public RectF getRect() {
        return rect;
    }

    @Override
    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public PowerCore getPowerCore() {
        return powerCore;
    }

    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    public ExtraPart getExtraPart() {
        return extraPart;
    }

    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public MainBody getMainBody() {
        return mainBody;
    }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }

    public PointF getEmitPoint() {
        return emitPoint;
    }

    public void setEmitPoint(PointF emitPoint) {
        this.emitPoint = emitPoint;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /** This method moves the whole ship. */
    public void move(){}

    /** This method shots a projectile from the ship. */
    public void shoot(){}

    public void update(double elapsedTime) {
        if (getSpeed() != 0) {
            ScreenRect.calcCenter();
            GraphicsUtils.MoveObjectResult result =
                    GraphicsUtils.moveObject(getCenterPoint(), getRect(), getSpeed(), getRotation() - Math.PI/2, elapsedTime);
            if (result.getNewObjPosition().x > 0 && result.getNewObjPosition().x < WorldRect.width &&
                    result.getNewObjPosition().y > 0 && result.getNewObjPosition().y < WorldRect.height) {
                if (result.getNewObjPosition().x - ScreenRect.width/2 > 0 && result.getNewObjPosition().x + ScreenRect.width/2 < WorldRect.width &&
                        result.getNewObjPosition().y - ScreenRect.height/2 > 0 && result.getNewObjPosition().y + ScreenRect.height/2 < WorldRect.height){
                    ScreenRect.centerPoint = new Point(Math.round(result.getNewObjPosition().x),
                            Math.round(result.getNewObjPosition().y));
                    ScreenRect.calcLeftPoint();
                    ScreenRect.calcRect();
                    ScreenRect.calcLeftPoint();
                    ScreenRect.calcRect();
                }
                else if (result.getNewObjPosition().x - ScreenRect.width/2 > 0 && result.getNewObjPosition().x + ScreenRect.width/2 < WorldRect.width){
                    ScreenRect.centerPoint = new Point(Math.round(result.getNewObjPosition().x),
                            ScreenRect.centerPoint.y);
                    ScreenRect.calcLeftPoint();
                    ScreenRect.calcRect();
                    ScreenRect.calcLeftPoint();
                    ScreenRect.calcRect();
                }
                else if (result.getNewObjPosition().y - ScreenRect.height/2 > 0 && result.getNewObjPosition().y + ScreenRect.height/2 < WorldRect.height){
                    ScreenRect.centerPoint = new Point(ScreenRect.centerPoint.x,
                            Math.round(result.getNewObjPosition().y));
                    ScreenRect.calcLeftPoint();
                    ScreenRect.calcRect();
                    ScreenRect.calcLeftPoint();
                    ScreenRect.calcRect();
                }

                setCenterPoint(result.getNewObjPosition());
                setRect(result.getNewObjBounds());

            }
        }

        if (mainBody != null && cannon != null && extraPart != null && engine != null && powerCore != null) {
            int shipWidth = extraPart.getExtraPartType().getImageWidth() + cannon.getCannonType().getImageWidth();
            int shipHeight = mainBody.getMainBodyType().getImageHeight() + engine.getEngineType().getImageHeight();
            setRect(new RectF(Math.round(getPositionX() - (.25 * shipWidth) * GC.XSCALE),
                    Math.round(getPositionY() - (.25 * shipHeight) * GC.YSCALE) +30,
                    Math.round(getPositionX() +(.25 * shipWidth)* GC.XSCALE),
                    Math.round(getPositionY() + (.25 * shipHeight) * GC.YSCALE)+30));
        }
        if (mainBody != null) {
            mainBody.update(getPositionX(), getPositionY(), GraphicsUtils.radiansToDegrees(getRotation()), getSpeed());
        }
        if (cannon != null) {
            int cannonAttachX = Math.round(cannon.getCannonType().getAttachPointX() * GC.XSCALE);
            int cannonAttachY = Math.round(cannon.getCannonType().getAttachPointY() * GC.YSCALE);

            int cannonCenterX = Math.round(cannon.getCannonType().getImageWidth() / 2 * GC.XSCALE);
            int cannonCenterY = Math.round(cannon.getCannonType().getImageHeight() / 2 * GC.YSCALE);

            int mainBodyAttachX = Math.round(mainBody.getMainBodyType().getCannonAttachX() * GC.XSCALE);
            int mainBodyAttachY = Math.round(mainBody.getMainBodyType().getCannonAttachY() * GC.YSCALE);

            int mainBodyCenterX = Math.round(mainBody.getMainBodyType().getImageWidth() / 2 * GC.XSCALE);
            int mainBodyCenterY = Math.round(mainBody.getMainBodyType().getImageHeight() / 2 * GC.YSCALE);


            int offsetX = (mainBodyAttachX - mainBodyCenterX) + (cannonCenterX - cannonAttachX);
            int offsetY = (mainBodyAttachY - mainBodyCenterY) + (cannonCenterY - cannonAttachY);
            PointF offset = GraphicsUtils.rotate(new PointF(offsetX, offsetY), getRotation());

            int xPos = getPositionX() + Math.round(offset.x);
            int yPos = getPositionY() + Math.round(offset.y);

            cannon.update(xPos, yPos, GraphicsUtils.radiansToDegrees(getRotation()), getSpeed());



            cannonAttachX = Math.round(cannon.getCannonType().getAttachPointX() * GC.XSCALE);
            cannonAttachY = Math.round(cannon.getCannonType().getAttachPointY() * GC.YSCALE);

            int cannonEmitX = Math.round(cannon.getCannonType().getEmitPointX()  * GC.XSCALE);
            int cannonEmitY = Math.round(cannon.getCannonType().getEmitPointY()  * GC.YSCALE);

            mainBodyAttachX = Math.round(mainBody.getMainBodyType().getCannonAttachX() * GC.XSCALE);
            mainBodyAttachY = Math.round(mainBody.getMainBodyType().getCannonAttachY() * GC.YSCALE);

            mainBodyCenterX = Math.round(mainBody.getMainBodyType().getImageWidth() / 2 * GC.XSCALE);
            mainBodyCenterY = Math.round(mainBody.getMainBodyType().getImageHeight() / 2 * GC.YSCALE);


            offsetX = (mainBodyAttachX - mainBodyCenterX) + (cannonEmitX - cannonAttachX);
            offsetY = (mainBodyAttachY - mainBodyCenterY) + (cannonEmitY - cannonAttachY);
            offset = GraphicsUtils.rotate(new PointF(offsetX, offsetY), getRotation());

            xPos = getPositionX() + Math.round(offset.x);
            yPos = getPositionY() + Math.round(offset.y);
            emitPoint = new PointF(xPos, yPos);

        }
        if (extraPart != null) {
            int extraAttachX = Math.round(extraPart.getExtraPartType().getAttachPointX() * GC.XSCALE);
            int extraAttachY = Math.round(extraPart.getExtraPartType().getAttachPointY() * GC.YSCALE);

            int mainBodyAttachX = Math.round(mainBody.getMainBodyType().getExtraAttachX() * GC.XSCALE);
            int mainBodyAttachY = Math.round(mainBody.getMainBodyType().getExtraAttachY() * GC.YSCALE);

            int mainCenterX = Math.round(mainBody.getMainBodyType().getImageWidth() / 2 * GC.XSCALE);
            int mainCenterY = Math.round(mainBody.getMainBodyType().getImageHeight() / 2 * GC.YSCALE);

            int extraPartCenterX = Math.round(extraPart.getExtraPartType().getImageWidth() / 2 * GC.XSCALE);
            int extraPartCenterY = Math.round(extraPart.getExtraPartType().getImageHeight() / 2 * GC.YSCALE);

            int offsetX = -1 * ((mainCenterX - mainBodyAttachX) + (extraAttachX - extraPartCenterX));
            int offsetY = (mainBodyAttachY - mainCenterY) + (extraPartCenterY- extraAttachY);
            PointF offset = GraphicsUtils.rotate(new PointF(offsetX, offsetY), getRotation());

            int xPos = getPositionX() + Math.round(offset.x);
            int yPos = getPositionY() + Math.round(offset.y);
            extraPart.update(xPos, yPos, GraphicsUtils.radiansToDegrees(getRotation()), getSpeed());
        }
        if (engine != null) {
            int engineAttachY = Math.round(engine.getEngineType().getAttachPointY() * GC.YSCALE);
            int mainBodyAttachY = Math.round(mainBody.getMainBodyType().getEngineAttachY() * GC.YSCALE);
            int engineCenterY = Math.round(engine.getEngineType().getImageHeight() / 2 * GC.YSCALE);
            int mainBodyCenterY = Math.round(mainBody.getMainBodyType().getImageHeight() / 2 * GC.YSCALE);
            int offsetY = (mainBodyAttachY - mainBodyCenterY) + (engineCenterY - engineAttachY);
            PointF offset = GraphicsUtils.rotate(new PointF(0, offsetY), getRotation());

            int xPos = getPositionX() + Math.round(offset.x);
            int yPos = getPositionY() + Math.round(offset.y);
            engine.update(xPos, yPos, GraphicsUtils.radiansToDegrees(getRotation()), getSpeed());
        }
    }

    @Override
    public void draw() {
        if (mainBody != null) {
            mainBody.draw();
        }
        if (cannon != null) {
            cannon.draw();
        }
        if (extraPart != null) {
            extraPart.draw();
        }
        if (engine != null) {
            engine.draw();
        }
    }
}
