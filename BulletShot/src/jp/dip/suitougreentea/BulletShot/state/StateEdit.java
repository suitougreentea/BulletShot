package jp.dip.suitougreentea.BulletShot.state;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import jp.dip.suitougreentea.BulletShot.Res;
import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.renderer.GLRenderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * TODO: -値を指定した場合生成はするが指定は-のままなので落ちる
 * @author suitougreentea
 *
 */

public class StateEdit extends BasicGameState {

    private int stateId;
    
    GLRenderer r;
    Terrain[][] t;
    int selectedX,selectedZ;
    
    public StateEdit(int i) {
        stateId = i;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        // TODO 自動生成されたメソッド・スタブ
        r = new GLRenderer();
        t = new Terrain[1][1];
        t[0][0] = new Terrain(0,1,0,0,null,0);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        r.init3D();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
            throws SlickException {
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Res.dback.draw();
        r.view3D();
        glEnable(GL_LIGHT0);
        r.drawFloors(t);
        glDisable(GL_LIGHT0);
        glEnable(GL_LIGHT1);
        r.drawWalls(t);
        glDisable(GL_LIGHT1);
        
        glEnable(GL_COLOR_MATERIAL);
        
        glColor4f(1f,0f,0f,1f);
        drawSelection(selectedX,selectedZ);
        glDisable(GL_DEPTH_TEST);
        glColor4f(1f,0f,0f,0.25f);
        drawSelection(selectedX,selectedZ);
        glEnable(GL_DEPTH_TEST);
        
        glDisable(GL_COLOR_MATERIAL);
        float fAmbient[] = {0.2f, 0.2f, 0.2f, 1.0f};
        glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, FloatBuffer.wrap(fAmbient));

        float fDiffuse[] = {0.8f, 0.8f, 0.8f, 1.0f};
        glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, FloatBuffer.wrap(fDiffuse));
        
        r.view2D();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        Input i = gc.getInput();
        
        if(i.isKeyDown(Input.KEY_1)){
            if(i.isKeyDown(Input.KEY_LEFT))r.cameraLeft();
            if(i.isKeyDown(Input.KEY_RIGHT))r.cameraRight();
            if(i.isKeyDown(Input.KEY_UP))r.cameraFront();
            if(i.isKeyDown(Input.KEY_DOWN))r.cameraBack();
            if(i.isKeyDown(Input.KEY_Q))r.cameraIn();
            if(i.isKeyDown(Input.KEY_W))r.cameraOut();
        }else{
            if(i.isKeyPressed(Input.KEY_LEFT))selectedX--;
            if(i.isKeyPressed(Input.KEY_RIGHT))selectedX++;
            if(i.isKeyPressed(Input.KEY_UP))selectedZ--;
            if(i.isKeyPressed(Input.KEY_DOWN))selectedZ++;
            
            if(i.isKeyPressed(Input.KEY_A)){
                checkAndMakeTerrain(selectedX,selectedZ);
                raiseHeight(selectedX,selectedZ);
            }
            if(i.isKeyPressed(Input.KEY_Z)){
                checkAndMakeTerrain(selectedX,selectedZ);
                if(isTerrainAvailable(selectedX,selectedZ))settleHeight(selectedX,selectedZ);
            }
            if(i.isKeyPressed(Input.KEY_Q)){
                checkAndMakeTerrain(selectedX,selectedZ);
                if(isTerrainAvailable(selectedX,selectedZ))
                changeType(selectedX,selectedZ);
            }
            if(i.isKeyPressed(Input.KEY_W)){
                checkAndMakeTerrain(selectedX,selectedZ);
                if(isTerrainAvailable(selectedX,selectedZ))
                rotateLeft(selectedX,selectedZ);
            }
            if(i.isKeyPressed(Input.KEY_E)){
                checkAndMakeTerrain(selectedX,selectedZ);
                if(isTerrainAvailable(selectedX,selectedZ))
                rotateRight(selectedX,selectedZ);
            }
        }
        i.clearKeyPressedRecord();
    }

    @Override
    public int getID() {
        // TODO 自動生成されたメソッド・スタブ
        return stateId;
    }
    
    private void drawSelection(int x,int z){
        int y = 0;
        if(x>=0&&x<t[0].length&&z>=0&&z<t.length)y = t[z][x].getHeight();
        float o = 0.01f;
        glLineWidth(2);
        glBegin(GL_LINE_LOOP);
        glVertex3f(x,y*0.5f+o,z);
        glVertex3f(x,y*0.5f+o,z+1);
        glVertex3f(x+1,y*0.5f+o,z+1);
        glVertex3f(x+1,y*0.5f+o,z);
        glEnd();
        glLineWidth(1);

    }
    
    private void checkAndMakeTerrain(int x,int z){
        if(x>=0&&x<t[0].length&&z>=0&&z<t.length){
        }
        else{
            int newWidth=t[0].length;
            int newHeight=t.length;
            int offsetX=0;
            int offsetZ=0;
            if(x>=t[0].length){
                newWidth=x+1;
                offsetX=0;
            }
            else if(x<0){
                newWidth=t[0].length-x;
                offsetX=-x;
            }
            if(z>=t.length){
                newHeight=z+1;
                offsetZ=0;
            }
            else if(z<0){
                newHeight=t.length-z;
                offsetZ=-z;
            }
            Terrain[][] nt = new Terrain[newHeight][newWidth];
            for(int iz=0;iz<newHeight;iz++){
                for(int ix=0;ix<newWidth;ix++){
                    nt[iz][ix] = new Terrain(0,0,0,0,null,0);
                }
            }
            for(int iz=0;iz<t.length;iz++){
                for(int ix=0;ix<t[0].length;ix++){
                    nt[iz+offsetZ][ix+offsetX] = t[iz][ix];
                }
            }
            t = nt;
            selectedX += offsetX;
            selectedZ += offsetZ;
            r.setCameraPosX(r.getCameraPosX()+offsetX);
            r.setCameraPosZ(r.getCameraPosZ()+offsetZ);
        }
    }
    
    private boolean isTerrainAvailable(int x,int z){
        if(t[z][x].getHeight() == 0)return false;
        else return true;
    }
    
    private void raiseHeight(int x,int z){
        t[z][x].setHeight(t[z][x].getHeight()+1);
    }
    private void settleHeight(int x,int z){
        if(t[z][x].getHeight()==0)return;
        int newHeight = t[z][x].getHeight()-1;
        t[z][x].setHeight(newHeight);
    }
    private void changeType(int x,int z){
        t[z][x].setType((t[z][x].getType()+1)%6);
        if(t[z][x].getType() == Terrain.TERRAIN_NORMAL || t[z][x].getType() == Terrain.TERRAIN_PYRAMID)t[z][x].setDirection(0);
    }
    private void rotateLeft(int x,int z){
        if(t[z][x].getType() == Terrain.TERRAIN_NORMAL || t[z][x].getType() == Terrain.TERRAIN_PYRAMID)return;
        t[z][x].setDirection((t[z][x].getDirection()+1)%4);
    }
    private void rotateRight(int x,int z){
        if(t[z][x].getType() == Terrain.TERRAIN_NORMAL || t[z][x].getType() == Terrain.TERRAIN_PYRAMID)return;
        t[z][x].setDirection((t[z][x].getDirection()-1+4)%4);
    }
}
