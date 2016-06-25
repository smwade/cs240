package edu.byu.cs.superasteroids.main_menu;

import android.graphics.Point;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.byu.cs.superasteroids.androids_game_definitions.AsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;
import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.androids_game_definitions.EngineType;
import edu.byu.cs.superasteroids.androids_game_definitions.ExtraPartType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelType;
import edu.byu.cs.superasteroids.androids_game_definitions.MainBodyType;
import edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType;
import edu.byu.cs.superasteroids.androids_game_objects.Cannon;
import edu.byu.cs.superasteroids.androids_game_objects.Engine;
import edu.byu.cs.superasteroids.androids_game_objects.ExtraPart;
import edu.byu.cs.superasteroids.androids_game_objects.MainBody;
import edu.byu.cs.superasteroids.androids_game_objects.PowerCore;
import edu.byu.cs.superasteroids.androids_game_objects.Ship;
import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.DataAccessObject;
import edu.byu.cs.superasteroids.database.DatabaseManager;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.AsteroidsProgram;
import edu.byu.cs.superasteroids.importer.GameDataImporter;

/**
 * Created by seanwade on 2/23/16.
 */
public class MainMenuController implements IMainMenuController {

    private IMainMenuView mainMenuView;

    public MainMenuController(IMainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void onQuickPlayPressed() {
        AsteroidsProgram asteroidsProgram = AsteroidsProgram.getInstance();
        DataAccessObject DAO = asteroidsProgram.getDAO();
        ContentManager content = ContentManager.getInstance();

        if (DAO !=  null) {
            asteroidsProgram.loadContent(content);


            asteroidsProgram.getShip().setMainBody(new MainBody(asteroidsProgram.getMainBodyImages().get(1)));
            asteroidsProgram.getShip().setCannon(new Cannon(asteroidsProgram.getCannonImages().get(1)));
            asteroidsProgram.getShip().setExtraPart(new ExtraPart(asteroidsProgram.getExtraPartImages().get(1)));
            asteroidsProgram.getShip().setPowerCore(new PowerCore(asteroidsProgram.getPowerCoreImages().get(1)));
            asteroidsProgram.getShip().setEngine(new Engine(asteroidsProgram.getEngineImages().get(1)));

            Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth() / 2,
                    DrawingHelper.getGameViewHeight() / 2);
            asteroidsProgram.getShip().getMainBody().setPositionX(gameViewCenter.x);
            asteroidsProgram.getShip().getMainBody().setPositionY(gameViewCenter.y);

            CannonType cannonType = DAO.readCannon().get(1);
            EngineType engineType = DAO.readEngine().get(1);
            ExtraPartType extraPartType = DAO.readExtraPart().get(1);
            MainBodyType mainBodyType = DAO.readMainBody().get(1);
            PowerCoreType powerCoreType = DAO.readPowerCore().get(1);

            asteroidsProgram.getShip().getMainBody().setMainBodyType(mainBodyType);
            asteroidsProgram.getShip().getCannon().setCannonType(cannonType);
            asteroidsProgram.getShip().getExtraPart().setExtraPartType(extraPartType);
            asteroidsProgram.getShip().getPowerCore().setPowerCoreType(powerCoreType);
            asteroidsProgram.getShip().getEngine().setEngineType(engineType);

            mainMenuView.startGame();
        }
    }

    @Override
    public IView getView() {
        return mainMenuView;
    }

    @Override
    public void setView(IView view) {
        mainMenuView = (IMainMenuView) view;
    }
}
