package jp.dip.suitougreentea.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import jp.dip.suitougreentea.util.BitmapFontOld;

@Deprecated
public class BitmapFontOld {
	Image font = null;
	int[] width;
	boolean drawShadow=true;
	int shadowX=2,shadowY=2;
	int height;
	int maxWidth;
	int marginX,marginY;   //TODO: Yまだ
	
	public BitmapFontOld(String path,Image fontimage) throws IOException, SlickException{
		this(new FileInputStream(path),fontimage);
	}
	
	public BitmapFontOld(FileInputStream stream,Image fontimage) throws IOException, SlickException{
		int array[] = new int[16*6];
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String s;
		String sb = "";
        while ((s = br.readLine()) != null) {
            sb += s;
        }
		String[] str = sb.replace(" ","").split(",");
		for(int i=0;i<16*6;i++)array[i] = Integer.valueOf(str[i]);
		this.width = array;
		font = fontimage;
		font.setFilter(Image.FILTER_NEAREST);
		height = font.getHeight()/6;
		maxWidth = font.getWidth()/16;
	}
	
	public BitmapFontOld(int[] width,Image fontimage) throws SlickException{
		this.width = width;
		font = fontimage;
		font.setFilter(Image.FILTER_NEAREST);
		height = font.getHeight()/6;
		maxWidth = font.getWidth()/16;
	}
	
	public void drawFormattedBase(String s,int x,int y,float scale){
		int i,sx,sy,w,wthis;
		char c;
		w=0;
		Color color = new Color(1f,1f,1f);
		for(i=0;i<s.length();i++){
			c = s.charAt(i);
			if(c == '&'){
				if(s.charAt(i+1) == '&')i++;
				else {
					String ct = s.substring(i+1,i+7);
					color = new Color(Integer.parseInt(ct, 16));
					i+=7;
					c = s.charAt(i);
				}
			}
			sx = ((c - 32) % 16) * maxWidth;
			sy = ((c - 32) / 16) * height;
			wthis=width[c-32];
			if(drawShadow)font.draw(x+w+shadowX, y+shadowY, x+w+(wthis*scale)+shadowX, y+(height*scale)+shadowY, sx,sy,sx+wthis,sy+height,new Color(0,0,0));
			font.draw(x+w, y, x+w+(wthis*scale), y+(height*scale), sx,sy,sx+wthis,sy+height,color);
			w += (wthis+marginX)*scale;
		}
	}
	
	public void drawBase(String s,int x,int y,float scale,Color color) throws SlickException{
		int i,sx,sy,w,wthis;
		char c;
		w=0;
		for(i=0;i<s.length();i++){
			c = s.charAt(i);
			sx = ((c - 32) % 16) * maxWidth;
			sy = ((c - 32) / 16) * height;
			wthis=width[c-32];
			if(drawShadow)font.draw(x+w+shadowX, y+shadowY, x+w+(wthis*scale)+shadowX, y+(height*scale)+shadowY, sx,sy,sx+wthis,sy+height,new Color(0,0,0));
			font.draw(x+w, y, x+w+(wthis*scale), y+(height*scale), sx,sy,sx+wthis,sy+height,color);
			w += (wthis+marginX)*scale;
		}
	}
	
	public void drawFormatted(String s,int x,int y,float scale) throws SlickException{
		String[] sn = getSplittedStringFormatted(s);
		for(int i=0;i<sn.length;i++)drawFormattedBase(sn[i],x,(int) (y+i*height*scale),scale);
	}
	public void drawFormatted(String s,int x,int y) throws SlickException{
		drawFormatted(s,x,y,1f);
	}
	public void draw(String s,int x,int y,float scale,Color color) throws SlickException{
		String[] sn = getSplittedString(s);
		for(int i=0;i<sn.length;i++)drawBase(sn[i],x,(int) (y+i*height*scale),scale,color);
	}
	public void draw(String s,int x,int y,float scale) throws SlickException{
		draw(s,x,y,scale,new Color(1f,1f,1f,1f));
	}
	public void draw(String s,int x,int y,Color color) throws SlickException{
		draw(s,x,y,1f,color);
	}
	public void draw(String s,int x,int y) throws SlickException{
		draw(s,x,y,1f,new Color(1f,1f,1f,1f));
	}
	
	public void drawRightFormatted(String s,int x,int y,float scale) throws SlickException{
		String[] sn = getSplittedStringFormatted(s);
		for(int i=0;i<sn.length;i++)drawFormatted(sn[i],(int) (x - getWidthFormatted(sn[i])*scale),(int) (y+i*height*scale),scale);
	}
	public void drawRightFormatted(String s,int x,int y) throws SlickException{
		drawRightFormatted(s,x,y,1f);
	}
	public void drawRight(String s,int x,int y,float scale,Color color) throws SlickException{
		String[] sn = getSplittedString(s);
		for(int i=0;i<sn.length;i++)draw(sn[i],(int) (x - getWidth(sn[i])*scale),(int) (y+i*height*scale),scale,color);
	}
	public void drawRight(String s,int x,int y,float scale) throws SlickException{
		drawRight(s,x,y,scale,new Color(1f,1f,1f,1f));
	}
	public void drawRight(String s,int x,int y,Color color) throws SlickException{
		drawRight(s,x,y,1f,color);
	}
	public void drawRight(String s,int x,int y) throws SlickException{
		drawRight(s,x,y,1f,new Color(1f,1f,1f,1f));
	}
	
	public void drawCenterFormatted(String s,int x,int y,int wid,float scale) throws SlickException{
		String[] sn = getSplittedStringFormatted(s);
		for(int i=0;i<sn.length;i++)drawFormatted(sn[i],(int) (x + wid/2f - getWidthFormatted(sn[i])*scale/2f) ,(int) (y+i*height*scale),scale);
	}
	public void drawCenterFormatted(String s,int x,int y,int wid) throws SlickException{
		drawCenterFormatted(s,x,y,wid,1f);
	}
	public void drawCenter(String s,int x,int y,int wid,float scale,Color color) throws SlickException{
		String[] sn = getSplittedString(s);
		for(int i=0;i<sn.length;i++)draw(sn[i],(int) (x + wid/2f - getWidth(sn[i])*scale/2f) ,(int) (y+i*height*scale),scale,color);
	}
	public void drawCenter(String s,int x,int y,int wid,float scale) throws SlickException{
		drawCenter(s,x,y,wid,scale,new Color(1f,1f,1f,1f));
	}
	public void drawCenter(String s,int x,int y,int wid,Color color) throws SlickException{
		drawCenter(s,x,y,wid,1f,color);
	}
	public void drawCenter(String s,int x,int y,int wid) throws SlickException{
		drawCenter(s,x,y,wid,1f,new Color(1f,1f,1f,1f));
	}
	public int getWidth(String s){
		char c;
		int wthis,w = 0;
		for(int i=0;i<s.length();i++){
			c = s.charAt(i);
			wthis=width[c-32];
			w += wthis;
		}
		return w;
	}
	public int getWidthFormatted(String str){
		String newStr = str.replaceAll("&[^&].....", "").replace("&&","&");
		return getWidth(newStr);
	}
	public void setFilter(int filter) throws SlickException{
		font.setFilter(filter);
	}

	public boolean isDrawShadow() {
		return drawShadow;
	}
	public void setDrawShadow(boolean drawShadow) {
		this.drawShadow = drawShadow;
	}
	public int getShadowX() {
		return shadowX;
	}
	public void setShadowX(int shadowX) {
		this.shadowX = shadowX;
	}
	public int getShadowY() {
		return shadowY;
	}
	public void setShadowY(int shadowY) {
		this.shadowY = shadowY;
	}
	public String[] getSplittedString(String s){
		return s.split("\n");
	}
	public String[] getSplittedStringFormatted(String s){
		String[] l = s.split("\n");
		String lastColor = "";
		for(int li=0;li<l.length;li++){
			l[li] = lastColor+l[li];
			String st = l[li];
			for(int i=0;i<st.length();i++){
				char c = st.charAt(i);
				if(c == '&'){
					if(s.charAt(i+1) != '&') {
						lastColor = s.substring(i,i+7);
						i+=7;
					}
				}
			}
		}
		return l;
	}
	
	public static void main(String[] args) throws SlickException{
		String p1="",p2="";
		if(args.length > 0)p1=args[0];
		if(args.length > 1)p2=args[1];
		
		AppGameContainer app = new AppGameContainer(new BitmapFontTest("BitmapFont Tester",p1,p2));
		
		app.setDisplayMode(640, 480, false);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.start();
	}

	public int getHeight() {
		return height;
	}

	public int getMaxWidth() {
		return maxWidth;
	}
	public static String getFormattedColor(Color c){
		return String.format("&%02x%02x%02x", c.getRed(),c.getGreen(),c.getBlue());
	}

    public void setMarginX(int x) {
        marginX = x;
    }

    public void setMarginY(int y) {
        marginY = y;
    }
}

class BitmapFontTest extends org.newdawn.slick.BasicGame {
	BitmapFontOld font = null;
	Image img;
	String imgFile;
	String widthFile;
	int[] width;
	char selectedChar = 33;
	int pointx=0,pointy=0;
	public BitmapFontTest(String title,String imgFile,String widthFile) throws SlickException {
		super(title);
		this.imgFile = imgFile;
		this.widthFile = widthFile;
	}

	private int[] getFilledArray(int i) {
		int[] array = new int[16*6];
		for(int ii=0;ii<16*6;ii++)array[ii] = i;
		return array;
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		font.draw(" !\"#$%&\'()*+,-./\n"+
		":;<=>?@[\\]^_`{|}~\n"+
		"THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.\n"+
		"the quick brown fox jumps over the lazy dog.\n"+
		"0123456789\n"+
		"(1)[2]<3>{4}",0,0);
		img.draw(0,font.getHeight()*7);
		Rectangle rect = new Rectangle(
				16,font.getHeight()*14,
				font.getWidth(String.valueOf(selectedChar))*3+1,font.getHeight()*3+1);
		arg1.setColor(new Color(1f,1f,1f,1f));
		arg1.draw(rect);
		
		Rectangle point = new Rectangle(
				pointx*font.getMaxWidth(),
				pointy*font.getHeight()+font.getHeight()*7,
				font.getMaxWidth(),
				font.getHeight());
		arg1.setColor(new Color(1f,0f,0f,0.5f));
		arg1.fill(point);
		font.draw(String.valueOf(selectedChar),16,font.getHeight()*14,3f);
		font.draw("WIDTH:"+font.getWidth(String.valueOf(selectedChar)),320,font.getHeight()*14);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		int array[] = new int[16*6];
		BufferedReader br = null;
		try {
			if(widthFile!=""){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(widthFile)));
			String s;
			String sb = "";
			while ((s = br.readLine()) != null) {
			    sb += s;
			}
			String[] str = sb.replace(" ","").split(",");
			for(int i=0;i<16*6;i++)array[i] = Integer.valueOf(str[i]);
			this.width = array;
			}
		} catch (FileNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		img = new Image(imgFile);
		if(widthFile.equals(""))this.width = getFilledArray(img.getWidth()/16);
		font = new BitmapFontOld(width,img);
		font.setDrawShadow(false);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_UP)&&pointy>0)pointy--;
		if(in.isKeyPressed(Input.KEY_DOWN)&&pointy<5)pointy++;
		if(in.isKeyPressed(Input.KEY_LEFT)&&pointx>0)pointx--;
		if(in.isKeyPressed(Input.KEY_RIGHT)&&pointx<15)pointx++;
		if(in.isKeyPressed(Input.KEY_Z)&&width[selectedChar-32] > 0){
			width[selectedChar-32]--;
			font = new BitmapFontOld(width,img);
		}
		if(in.isKeyPressed(Input.KEY_X)&&width[selectedChar-32] < font.getMaxWidth()){
			width[selectedChar-32]++;
			font = new BitmapFontOld(width,img);
		}
		selectedChar = (char) (pointx+pointy*16+32);
	}
}
