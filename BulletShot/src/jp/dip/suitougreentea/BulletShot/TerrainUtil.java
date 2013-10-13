package jp.dip.suitougreentea.BulletShot;

public class TerrainUtil {
    public static float getHeight(Stage s, float x, float z){
        Terrain t = s.getTerrain((int)x,(int)z);
        return getHeight(x-(int)x, z-(int)z, t.getType(),t.getHeight(),t.getDirection());
    }
    
    
    public static float getHeight(float x, float z, int type, int height, int direction){
        float result = 0f;
        if(type==Terrain.TERRAIN_LOWSLOPE){
            switch(direction){
            case Terrain.DIRECTION_TOP: result = 0.5f*(1-z); break;
            case Terrain.DIRECTION_LEFT: result = 0.5f*(1-x); break;
            case Terrain.DIRECTION_BOTTOM: result = 0.5f*z; break;
            case Terrain.DIRECTION_RIGHT: result = 0.5f*x; break;
            }
        }else if(type==Terrain.TERRAIN_HIGHSLOPE || type==Terrain.TERRAIN_SINGLESLOPE){
            switch(direction){
            case Terrain.DIRECTION_TOPLEFT: result = 1-0.5f*(x+z); break;
            case Terrain.DIRECTION_BOTTOMLEFT: result = 0.5f-0.5f*(x-z); break;
            case Terrain.DIRECTION_BOTTOMRIGHT: result = 0.5f*(x+z); break;
            case Terrain.DIRECTION_TOPRIGHT: result = 0.5f+0.5f*(x-z); break;
            }
            if(type==Terrain.TERRAIN_SINGLESLOPE){
                result -= 0.5f;
                if(result<0f)result=0f;
            }
        }else if(type==Terrain.TERRAIN_DOUBLESLOPE){
            switch(direction){
            case Terrain.DIRECTION_TOPLEFT: if(x>z) result = 0.5f*(1-x); else result = 0.5f*(1-z); break;
            case Terrain.DIRECTION_BOTTOMLEFT: if(x+z>1) result = 0.5f*(1-x); else result = 0.5f*z; break;
            case Terrain.DIRECTION_BOTTOMRIGHT: if(x>z) result = 0.5f*z; else result = 0.5f*x; break;
            case Terrain.DIRECTION_TOPRIGHT: if(x+z>1) result = 0.5f*z; else result = 0.5f*(1-x); break;
            }
        }else if(type==Terrain.TERRAIN_PYRAMID){
            if(z<=0.5f && z<=x && x<=1-z) result = 0.5f*z;  //TOP
            else if(z>=0.5f && 1-z<=x && x<=z) result = 0.5f*(1-z);  //BOTTOM
            else if(x<=0.5f && x<=z && z<=1-x) result = 0.5f*x;  //LEFT
            else if(x>=0.5f && 1-x<=z && z<=x) result = 0.5f*(1-x);  //RIGHT
        }
        
        result += height*0.5f;
        return result;
    }
}
