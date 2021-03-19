package pacman.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import pacman.Handler;

public class Dots extends Entity{
 
	public Rectangle[] dots = new Rectangle[240];
	public Rectangle[] emptydots = new Rectangle[240];
	public Rectangle[] bigdots = new Rectangle[4];
	public Rectangle[] emptybigdots = new Rectangle[4];
	private int nDots=0;
	private int nBig=0;
	public Dots(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.x=x;
		this.y=y;
		this.bounds.width = width;
		this.bounds.height = height;

		
		makeDots();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(226,159,130));
		for(int k=0;k<dots.length;k++) {
			
			g.fillRect(dots[k].x, dots[k].y, dots[k].width, dots[k].height);
		
		}
		for(int k=0;k<bigdots.length;k++) {
			g.fillRect(bigdots[k].x, bigdots[k].y, bigdots[k].width, bigdots[k].height);
		}
		
	}


	
	
	public void makeDots() {

		nDots=0;
		nBig=0;
		dots = emptydots;
		bigdots = emptybigdots;
		
		//1st row
		for(int x=33,y=102;nDots<12;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,y,5,5);
		}
		for(int x=383,y=102;nDots<24;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,y,5,5);
		}
		//2nd row
		dots[nDots]= new Rectangle(33,102+25,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+25,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+25,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+25,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+25,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+25,5,5);
		nDots++;
		//3rd row
		dots[nDots]= new Rectangle(33+125,102+50,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+50,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+50,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+50,5,5);
		nDots++;
		//4th row
		dots[nDots]= new Rectangle(33,105+75,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+75,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+75,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+75,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+75,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+75,5,5);
		nDots++;
		//5th row
		for(int x=33,y=100;nDots<66;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,102+y,5,5);
		}
		//6th row
		dots[nDots]= new Rectangle(33,102+125,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+125,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+200,102+125,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+425,102+125,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+125,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+125,5,5);
		nDots++;
		//7th row
		dots[nDots]= new Rectangle(33,102+150,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+150,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+200,102+150,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+425,102+150,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+150,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+150,5,5);
		nDots++;
		//8th row
		for(int x=33,y=175;nDots<84;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,102+y,5,5);
		}
		for(int x=33,y=175;nDots<88;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+200,102+y,5,5);
		}
		for(int x=33,y=175;nDots<92;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+350,102+y,5,5);
		}
		for(int x=33,y=175;nDots<98;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+500,102+y,5,5);
		}
		//9th to 18th row
		for(int x=33,y=200;nDots<120;x+=0,y+=25) {
			dots[nDots]= new Rectangle(x+125,102+y,5,5);
			nDots++;
			dots[nDots]= new Rectangle(x+500,102+y,5,5);
			nDots++;
			
		}
		//19th row
		for(int x=33,y=475;nDots<132;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,102+y,5,5);
		}
		for(int x=33,y=475;nDots<144;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+350,102+y,5,5);
		}
		//20th row
		dots[nDots]= new Rectangle(33,102+500,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+500,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+500,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+500,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+500,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+500,5,5);
		nDots++;
		//21st row
		dots[nDots]= new Rectangle(33,102+525,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+525,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+525,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+525,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+525,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+525,5,5);
		nDots++;
		//22nd row
		
		for(int x=33,y=550;nDots<158;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+25,102+y,5,5);
		}
		for(int x=33,y=550;nDots<165;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+125,102+y,5,5);
		}
		for(int x=33,y=550;nDots<172;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+350,102+y,5,5);
		}
		for(int x=33,y=550;nDots<174;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+575,102+y,5,5);
		}
		//23rd row
		dots[nDots]= new Rectangle(33+50,102+575,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+575,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+200,102+575,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+425,102+575,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+575,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+575,102+575,5,5);
		nDots++;
		//24th row
		dots[nDots]= new Rectangle(33+50,102+600,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+125,102+600,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+200,102+600,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+425,102+600,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+500,102+600,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+575,102+600,5,5);
		nDots++;
		//25th
		for(int x=33,y=625;nDots<192;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,102+y,5,5);
		}
		for(int x=33,y=625;nDots<196;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+200,102+y,5,5);
		}
		for(int x=33,y=625;nDots<200;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+350,102+y,5,5);
		}
		for(int x=33,y=625;nDots<206;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x+500,102+y,5,5);
		}
		//26th
		dots[nDots]= new Rectangle(33,102+650,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+650,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+650,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+650,5,5);
		nDots++;
		//27th row
		dots[nDots]= new Rectangle(33,102+675,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+275,102+675,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+350,102+675,5,5);
		nDots++;
		dots[nDots]= new Rectangle(33+625,102+675,5,5);
		nDots++;
		//28th row
		for(int x=33,y=700;nDots<240;nDots++,x+=25,y+=0) {
			dots[nDots]= new Rectangle(x,102+y,5,5);
		}
		
		//ENERGIZERS!
		bigdots[nBig] = new Rectangle(27,148,17,17);
		nBig++;
		bigdots[nBig] = new Rectangle(652,148,17,17);
		nBig++;
		bigdots[nBig] = new Rectangle(27,644,17,17);
		nBig++;
		bigdots[nBig] = new Rectangle(652,644,17,17);
		nBig++;
		
	}
	
	public void removeDots(Rectangle dotRemove) {
		for(int k=0;k<dots.length;k++) {
			if(dots[k]==dotRemove) {
				Rectangle[] dots2 = new Rectangle[dots.length-1];
				for(int l=k;l+1<dots.length;l++) {
					dots[l]=dots[l+1];
				}
				for(int l=0;l<dots2.length;l++) {
					dots2[l]=dots[l];
				}
				dots=dots2;
			}
		}
		for(int k=0;k<bigdots.length;k++) {
			if(bigdots[k]==dotRemove) {
				Rectangle[] dots2 = new Rectangle[bigdots.length-1];
				for(int l=k;l+1<bigdots.length;l++) {
					bigdots[l]=bigdots[l+1];
				}
				for(int l=0;l<dots2.length;l++) {
					dots2[l]=bigdots[l];
				}
				bigdots=dots2;
			}
		}

	}
	
	public int getAllDots() {
		return nDots+nBig;
	}
	
}
