/*
	 Ball.java (Java)
	 
	 Objetivo: Define a bola do Jogo Pong.
	 
	 Site: http://www.dirackslounge.online
	 
	 Versão 1.0
	 
	 Programador: Rodolfo Dirack 23/08/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.
*/

package GamePong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball{

	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCALE;
	private int ball_width;
	private int ball_height;
	private int ball_x;
	private int ball_y;
	private boolean right;
	private boolean left;
	private int numero;
	private int VELOCITY=2;
	private double dx;
	private double dy;

	public Ball(int SCREEN_WIDTH, int SCREEN_HEIGHT, int SCALE){
		this.SCREEN_WIDTH = SCREEN_WIDTH*SCALE;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT*SCALE;
		this.SCALE = SCALE;
		this.dx = new Random().nextGaussian();
		this.dy = new Random().nextGaussian();
	}

	public void setPongPlayerSizeAndPosition(){
		this.ball_width=5*this.SCALE;
		this.ball_height=5*this.SCALE;
		this.ball_x = (int)(this.SCREEN_WIDTH/2);
		this.ball_y = (int)(this.SCREEN_HEIGHT/2);
	}

	public void drawPongPlayer(Graphics g){
		g.fillRect(this.ball_x,this.ball_y,this.ball_width,this.ball_height);
	}

	public void updatePongPlayer(){
		this.ball_x += this.dx * this.VELOCITY;
		this.ball_y += this.dy * this.VELOCITY;
	}

}
