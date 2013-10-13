package jp.dip.suitougreentea.BulletShot;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.effect.Effect;
import jp.dip.suitougreentea.BulletShot.effect.EffectDash;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

public class Terrain {  //TODO: setする必要性？
    private int type;
    public static final int TERRAIN_NORMAL = 0;
    public static final int TERRAIN_LOWSLOPE = 1;
    public static final int TERRAIN_HIGHSLOPE = 2;
    public static final int TERRAIN_SINGLESLOPE = 3;
    public static final int TERRAIN_DOUBLESLOPE = 4;
    //public static final int TERRAIN_TRIANGLE;
    public static final int TERRAIN_PYRAMID = 5;
    
    private int height;
    
    private int direction;
    public static final int DIRECTION_TOPLEFT = 0;
    public static final int DIRECTION_BOTTOMLEFT = 1;
    public static final int DIRECTION_BOTTOMRIGHT = 2;
    public static final int DIRECTION_TOPRIGHT = 3;
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_BOTTOM = 2;
    public static final int DIRECTION_RIGHT = 3;
    public static final int DIRECTION_NULL = -1;
    
    private int bumper;
    public static final int BUMPER_LEFT = 1;
    public static final int BUMPER_RIGHT = 2;
    public static final int BUMPER_TOP = 4;
    public static final int BUMPER_BOTTOM = 8;
    public static final int BUMPER_VERTICAL = 16;
    public static final int BUMPER_HORIZONTAL = 32;
    public static final int BUMPER_SLASH = 64;
    public static final int BUMPER_BACKSLASH = 128;
    public static final int BUMPER_SLASH_TOPLEFT = 256;
    public static final int BUMPER_SLASH_BOTTOMRIGHT = 512;
    public static final int BUMPER_BACKSLASH_TOPRIGHT = 1024;
    public static final int BUMPER_BACKSLASH_BOTTOMLEFT = 2048;
    
    //private int data;
    public static final int DATA_SAND = 1;
    public static final int DATA_NEEDLE = 2;
    public static final int DATA_GRASS_LIGHT = 3;
    public static final int DATA_GRASS_STRONG = 4;
    public static final int DATA_DASH_SLOW = 5;
    public static final int DATA_DASH_FAST = 6;
    public static final int DATA_JUMP = 7;
    public static final int DATA_GRASS_LIGHT_TOP = 16;
    public static final int DATA_GRASS_LIGHT_TOPLEFT = 17;
    public static final int DATA_GRASS_LIGHT_LEFT = 18;
    public static final int DATA_GRASS_LIGHT_BOTTOMLEFT = 19;
    public static final int DATA_GRASS_LIGHT_BOTTOM = 20;
    public static final int DATA_GRASS_LIGHT_BOTTOMRIGHT = 21;
    public static final int DATA_GRASS_LIGHT_RIGHT = 22;
    public static final int DATA_GRASS_LIGHT_TOPRIGHT = 23;
    public static final int DATA_GRASS_STRONG_TOP = 24;
    public static final int DATA_GRASS_STRONG_TOPLEFT = 25;
    public static final int DATA_GRASS_STRONG_LEFT = 26;
    public static final int DATA_GRASS_STRONG_BOTTOMLEFT = 27;
    public static final int DATA_GRASS_STRONG_BOTTOM = 28;
    public static final int DATA_GRASS_STRONG_BOTTOMRIGHT = 29;
    public static final int DATA_GRASS_STRONG_RIGHT = 30;
    public static final int DATA_GRASS_STRONG_TOPRIGHT = 31;
    public static final int DATA_DASH_SLOW_TOP = 32;
    public static final int DATA_DASH_SLOW_TOPLEFT = 33;
    public static final int DATA_DASH_SLOW_LEFT = 34;
    public static final int DATA_DASH_SLOW_BOTTOMLEFT = 35;
    public static final int DATA_DASH_SLOW_BOTTOM = 36;
    public static final int DATA_DASH_SLOW_BOTTOMRIGHT = 37;
    public static final int DATA_DASH_SLOW_RIGHT = 38;
    public static final int DATA_DASH_SLOW_TOPRIGHT = 39;
    public static final int DATA_DASH_FAST_TOP = 40;
    public static final int DATA_DASH_FAST_TOPLEFT = 41;
    public static final int DATA_DASH_FAST_LEFT = 42;
    public static final int DATA_DASH_FAST_BOTTOMLEFT = 43;
    public static final int DATA_DASH_FAST_BOTTOM = 44;
    public static final int DATA_DASH_FAST_BOTTOMRIGHT = 45;
    public static final int DATA_DASH_FAST_RIGHT = 46;
    public static final int DATA_DASH_FAST_TOPRIGHT = 47;
    public static final int DATA_KICK_TOP = 48;
    public static final int DATA_KICK_TOPLEFT = 49;
    public static final int DATA_KICK_LEFT = 50;
    public static final int DATA_KICK_BOTTOMLEFT = 51;
    public static final int DATA_KICK_BOTTOM = 52;
    public static final int DATA_KICK_BOTTOMRIGHT = 53;
    public static final int DATA_KICK_RIGHT = 54;
    public static final int DATA_KICK_TOPRIGHT = 55;
    public static final int DATA_CONVEYOR_TOP = 56;
    public static final int DATA_CONVEYOR_LEFT = 57;
    public static final int DATA_CONVEYOR_BOTTOM = 58;
    public static final int DATA_CONVEYOR_RIGHT = 59;
    public static final int DATA_AIR_VERTICAL = 64;
    public static final int DATA_AIR_HORIZONTAL = 65;
    
    private int chara;
    
    private RigidBody slopeRigidBody,floorRigidBody;
    private Effect effect;
    
    public Terrain(int type,int height,int direction,int bumper,Effect effect,int chara){
        this.type = type;
        this.height = height;
        this.direction = direction;
        this.bumper = bumper;
        this.effect = effect;
        this.chara = chara;
        init();
    }
    
    private void init(){
        //if(data==5)effect = new EffectDash(0,0);
    }
    
    public void generateRigidBody(int x, int z){
        if(type != Terrain.TERRAIN_NORMAL){
            ObjectArrayList<Vector3f> points = BulletTerrainBuilder.getPoints(this);    //TODO: 渡すものが多すぎるかもしれない
            CollisionShape slopeShape = new ConvexHullShape(points);
            DefaultMotionState slopeMotionState = new DefaultMotionState(new Transform(new Matrix4f(
              new Quat4f(0,0,0,1f),new Vector3f(x,height*0.5f,z), 1f)));
            RigidBodyConstructionInfo slopeRigidBodyCI = new RigidBodyConstructionInfo(0,slopeMotionState,slopeShape,new Vector3f(0,0,0));
            slopeRigidBody = new RigidBody(slopeRigidBodyCI);
            //dynamicsWorld.addRigidBody(slopeRigidBody);
            
            //dynamicsWorld.addRigidBody(slopeRigidBody,(short)0x1,(short)0x2);
            slopeRigidBody.setActivationState(RigidBody.ISLAND_SLEEPING);
            slopeRigidBody.setRestitution(1.0f);
        }
        
        CollisionShape boxShape = new BoxShape(new Vector3f(0.5f,height*0.25f,0.5f));
        DefaultMotionState boxMotionState = 
                new DefaultMotionState(new Transform(
                        new Matrix4f(new Quat4f(0,0,0,1),new Vector3f(x+0.5f,height*0.25f,z+0.5f), 1f)
                ));
        RigidBodyConstructionInfo boxRigidBodyCI = new RigidBodyConstructionInfo(0,boxMotionState,boxShape,new Vector3f(0,0,0));
        floorRigidBody = new RigidBody(boxRigidBodyCI);
        
        //dynamicsWorld.addRigidBody(boxRigidBody,(short)0x1,(short)0x2);
        floorRigidBody.setActivationState(RigidBody.ISLAND_SLEEPING);
        floorRigidBody.setRestitution(1.0f);
    }
    
    public boolean isBumperAvailable(int bumperdata){
        return (this.bumper & bumperdata) == bumperdata;
    }
    //public static final int a=0;

    public int getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public int getDirection() {
        return direction;
    }

    public int getBumper() {
        return bumper;
    }

    /*public int getData() {
        return data;
    }*/

    public int getChara() {
        return chara;
    }

    public void setChara(int chara) {
        this.chara = chara;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setBumper(int bumper) {
        this.bumper = bumper;
    }

    /*public void setData(int data) {
        this.data = data;
    }*/

    public RigidBody getSlopeRigidBody() {
        return slopeRigidBody;
    }

    public void setSlopeRigidBody(RigidBody slopeRigidBody) {
        this.slopeRigidBody = slopeRigidBody;
    }

    public RigidBody getFloorRigidBody() {
        return floorRigidBody;
    }

    public void setFloorRigidBody(RigidBody floorRigidBody) {
        this.floorRigidBody = floorRigidBody;
    }

    public Effect getEffect() {
        return effect;
    }
}
