package jp.dip.suitougreentea.BulletShot.state;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.BulletShot;
import jp.dip.suitougreentea.BulletShot.PredictStage;
import jp.dip.suitougreentea.BulletShot.Res;
import jp.dip.suitougreentea.BulletShot.Stage;
import jp.dip.suitougreentea.BulletShot.renderer.GLRenderer;
import jp.dip.suitougreentea.BulletShot.test.StageGenerator;
import jp.dip.suitougreentea.BulletShot.test.StageGeneratorFlat;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bulletphysics.linearmath.Transform;

public class StateGame extends BasicGameState {

    private int stateId;

    public StateGame(int i) {
        stateId = i;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        r = new GLRenderer();
        initPhysics();
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        r.init3D();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Res.dback.draw();
        r.view3D();
        if (state == STATE_SLEEPINGPHYSICS) {
            r.draw(stage, predict, predicttimer);
        } else {
            r.draw(stage, null, 0);
        }
        r.view2D();
        // gui.draw();

        if (state == STATE_SHOOT) {
            Res.guishoot.draw(0, 0, 136, 96, 0, 0, 136, 96);
            Res.guishoot.draw(96, 0, 128, 96, 136, 0, 168, 96);
            int p;
            if (shootpowertimer > 64) {
                p = 128 - shootpowertimer;
            } else {
                p = shootpowertimer;
            }
            Res.guishoot.draw(104, 80 - p, 120, 80, 232, 80 - p, 248, 80);
        }

        Vector3f pos = stage.getCirclePos();
        Res.debugfont.draw("Space: Reset", 16, 32);
        Res.debugfont.draw(String.format("x:%f\ny:%f\nz:%f\nh:%d\nv:%d", pos.x, pos.y, pos.z, hRot, vRot), 16, 48);
        Res.debugfont.draw(String.format("manifolds:%d", stage.getDynamicsWorld().getDispatcher().getNumManifolds()), 16, 120);
        Res.debugfont.draw(String.format("Cam:%b", cameraMoving), 16, 160);
    }

    private GLRenderer r;
    //private GUIRenderer gui = new GUIRenderer();

    // StageGenerator gen = new StageGeneratorTest();
    private StageGenerator gen = new StageGeneratorFlat(20, 20, 1);
    private Stage stage = new Stage(gen.generate(), 0, 0);
    private PredictStage predictStage = new PredictStage(gen.generate(), 0, 0);

    private Transform[] predict;

    private int tHroti, tHrotd, tVroti, tVrotd;

    private int state;
    private static final int STATE_SLEEPINGPHYSICS = 1;
    private static final int STATE_SHOOT = 2;
    private static final int STATE_WORKINGPHYSICS = 3;
    private boolean cameraMoving;
    private boolean powerDeciding;

    private int shootpowertimer = 0;
    //private int shootwaittimer = 0;
    private int hRot = 0;
    private int vRot = 60;

    private int predicttimer;

    public void initPhysics() {
        stage.initPhysics();
        predictStage.initPhysics();

        Transform t = new Transform();
        stage.getPlayer().getMotionState().getWorldTransform(t);
        predictStage.setKeepingPlayerPosition(t.origin);

        refreshPrediction();
        state = STATE_SLEEPINGPHYSICS;
        BulletShot.LOGGER.info("Reset physics.");
    }

    public void refreshPrediction() {
        predict = predictStage.getPredictedPath(hRot, vRot);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input i = gc.getInput();
        r.incrementTimer();

        if (i.isKeyPressed(Input.KEY_SPACE)) {
            initPhysics();
        }
        if (i.isKeyPressed(Input.KEY_C)) {
            r.changeCameraPos();
        }

        if (state == STATE_SLEEPINGPHYSICS) {
            predicttimer = (predicttimer + 1) % 6;
            // if(i.isKeyPressed(Input.KEY_X))stage.shoot(hRot,vRot);
            if (i.isKeyPressed(Input.KEY_X)) {
                state = STATE_SHOOT;
            }

            if (i.isKeyPressed(Input.KEY_Q)) {
                Transform t = new Transform();
                stage.getPlayer().getMotionState().getWorldTransform(t);
                predictStage.setKeepingPlayerPosition(t.origin);
                predict = predictStage.getPredictedPath(hRot, vRot);
            }
            if (i.isKeyDown(Input.KEY_A)) {
                cameraMoving = true;
                if (i.isKeyDown(Input.KEY_LEFT)) {
                    r.cameraLeft();
                }
                if (i.isKeyDown(Input.KEY_RIGHT)) {
                    r.cameraRight();
                }
                if (i.isKeyDown(Input.KEY_UP)) {
                    r.cameraFront();
                }
                if (i.isKeyDown(Input.KEY_DOWN)) {
                    r.cameraBack();
                }
                if (i.isKeyDown(Input.KEY_Q)) {
                    r.cameraIn();
                }
                if (i.isKeyDown(Input.KEY_W)) {
                    r.cameraOut();
                }
            } else {
                cameraMoving = false;
                if (i.isKeyDown(Input.KEY_LEFT)) {
                    if (tHrotd == 0) {
                        hRot = (hRot + 360 - 1) % 360;
                        refreshPrediction();
                    } else if (tHrotd >= 15 && (tHrotd - 15) % 3 == 0) {
                        hRot = (hRot + 360 - 3) % 360;
                        refreshPrediction();
                    }
                    tHrotd++;
                } else {
                    tHrotd = 0;
                }
                if (i.isKeyDown(Input.KEY_RIGHT)) {
                    if (tHroti == 0) {
                        hRot = (hRot + 1) % 360;
                        refreshPrediction();
                    } else if (tHroti >= 15 && (tHroti - 15) % 3 == 0) {
                        hRot = (hRot + 3) % 360;
                        refreshPrediction();
                    }
                    tHroti++;
                } else {
                    tHroti = 0;
                }
                if (i.isKeyDown(Input.KEY_UP)) {
                    if (tVrotd == 0) {
                        vRot = (vRot + 91 - 1) % 91;
                        refreshPrediction();
                    } else if (tVrotd >= 15 && (tVrotd - 15) % 3 == 0) {
                        vRot = (vRot + 91 - 3) % 91;
                        refreshPrediction();
                    }
                    tVrotd++;
                } else {
                    tVrotd = 0;
                }
                if (i.isKeyDown(Input.KEY_DOWN)) {
                    if (tVroti == 0) {
                        vRot = (vRot + 1) % 91;
                        refreshPrediction();
                    } else if (tVroti >= 15 && (tVroti - 15) % 3 == 0) {
                        vRot = (vRot + 3) % 91;
                        refreshPrediction();
                    }
                    tVroti++;
                } else {
                    tVroti = 0;
                }
                if (i.isKeyPressed(Input.KEY_W)) {
                    vRot = 0;
                    refreshPrediction();
                }
                if (i.isKeyPressed(Input.KEY_E)) {
                    vRot = 60;
                    refreshPrediction();
                }
            }
        } else if (state == STATE_SHOOT) {
            if (powerDeciding) {
                shootpowertimer++;
                if (shootpowertimer == 128) {
                    stage.shoot(5, hRot, vRot);
                    powerDeciding = false;
                    shootpowertimer = 0;
                    state = STATE_WORKINGPHYSICS;
                } else if (i.isKeyPressed(Input.KEY_X)) {
                    stage.shoot((shootpowertimer > 64) ? (128 - shootpowertimer) : shootpowertimer, hRot, vRot);
                    powerDeciding = false;
                    shootpowertimer = 0;
                    state = STATE_WORKINGPHYSICS;
                }
            }
            if (i.isKeyPressed(Input.KEY_Z)) {
                state = STATE_SLEEPINGPHYSICS;
            }

            if (i.isKeyPressed(Input.KEY_X)) {
                /*
                 * stage.shoot(hRot,vRot); state = STATE_SLEEPINGPHYSICS;
                 */
                powerDeciding = true;
            }

        } else if (state == STATE_WORKINGPHYSICS) {
            stage.update();
            if (!stage.getPlayer().getRigidBody().isActive()) {
                predictStage.setKeepingPlayerPosition(stage.getCirclePos());
                // refreshPrediction();
                refreshPrediction();
                state = STATE_SLEEPINGPHYSICS;
            }
        }
        i.clearKeyPressedRecord();
    }

    @Override
    public int getID() {
        return stateId;
    }

}
