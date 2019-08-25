/*
	 Game.java (Java)
	 
	 Objetivo: Estabelecer o jogo com os métodos de inicialização e renderização.
	 
	 Site: http://www.dirackslounge.online
	 
	 Versão 1.0
	 
	 Programador: Rodolfo Dirack 14/07/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.
*/

package GamePong;

import GamePong.Screen;
import GamePong.Player;
import GamePong.Enemy;
import GamePong.Ball;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import GamePong.Sound;

public class Game implements Runnable, KeyListener{

	public boolean isRunning=false;
	private int frame=0;
	public static Screen screen;
	public String TITLE;
	public int WIDTH, HEIGHT, SCALE;
	private BufferStrategy bs;
	private BufferedImage layer;

	private Player player;
	private Enemy enemy;
	public Ball ball;

	public Game(String TITLE, int WIDTH, int HEIGHT, int SCALE){
		this.TITLE = TITLE;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.SCALE = SCALE;
		this.layer = new BufferedImage(this.WIDTH,this.HEIGHT,BufferedImage.TYPE_INT_RGB);
		this.screen = new Screen(TITLE, WIDTH, HEIGHT, SCALE);
		this.player = new Player(WIDTH,HEIGHT,SCALE);
		this.enemy = new Enemy(WIDTH,HEIGHT,SCALE);
		this.ball = new Ball(WIDTH,HEIGHT,SCALE);
		this.screen.canvas.addKeyListener(this);
		screen.showScreen();
		this.screen.canvas.requestFocus();
	}

	private void restartGameAfterAPoint(){
		this.ball.setPongPlayerPosition(this.ball.SCREEN_WIDTH/2,this.ball.SCREEN_HEIGHT/2);
		this.ball.pongPlayerSpeed=this.ball.v0;
		this.ball.giveBallAnStartingAngle();
	}

	public void updateGame(){
		player.updatePongPlayer();
		enemy.updatePongPlayer(this.ball.getBallXPosition());
		ball.updatePongPlayer(enemy.getPongPlayerRectangle(),player.getPongPlayerRectangle());

		if(this.ball.getBallYPosition() < 0 ){
			this.restartGameAfterAPoint();
			this.player.givePongPlayerAPoint();
			Sound.playerPoint.play();
		}else if(this.ball.getBallYPosition() > (this.HEIGHT * this.SCALE)){
			this.restartGameAfterAPoint();
			this.enemy.givePongPlayerAPoint();
			Sound.enemyPoint.play();
		}
	}

	public void renderizeGame(){
		this.bs = this.screen.canvas.getBufferStrategy();

		if (this.bs == null){
			this.screen.canvas.createBufferStrategy(3);
			return;
		}

		Graphics g = this.layer.getGraphics();
		g.setColor(Color.BLACK);
		this.screen.drawBackground(g,this.enemy.score,this.player.score);
		g.setColor(Color.WHITE);
		this.player.drawPongPlayer(g);
		g.setColor(Color.WHITE);
		this.enemy.drawPongPlayer(g);
		g.setColor(Color.WHITE);
		this.ball.drawPongPlayer(g);

		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);

		this.bs.show();		
	}

	public synchronized void startGame(){
		this.isRunning = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run(){

		while(isRunning){
			frame++;
			updateGame();
			renderizeGame();
			try{
				Thread.sleep(1000/60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.moveRight=true;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			player.moveLeft=true;
		}
	}

	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.moveRight=false;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			player.moveLeft=false;
		}
	}

	public void keyTyped(KeyEvent e){}

}
