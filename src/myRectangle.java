import java.awt.*;
import java.awt.geom.Rectangle2D;

public class myRectangle extends Rectangle implements myShape {
	private Point start = new Point(0, 0);
	private Color bg; 
	private Color fg;
	private Color color;
	private boolean filled = false;
	int width;
	int height;
	private int x;
	private int y;
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private boolean draw3D= false; 
	private Rectangle2D myRect;
	 
	// private Shape rectShape;

	public myRectangle() {
		super();

	}

	public void draw(Graphics g) {

		g.setColor(color);

		if (filled == false) {
			myRect = new Rectangle2D.Float(x, y, width, height);
			//g.drawRect(x, y, width, height);
		} else if(filled==true){
			myRect = new Rectangle2D.Float(x, y, width, height);
			//g.fillRect(x, y, width, height);
			// g.drawRect(x, y, width, height);
		}else if(filled == true && draw3D == true){
			
			g.setColor(fg);
			g.fill3DRect(x, y, width, height, true);
			g.setColor(bg);
		}

		/*
		 * for (int i = 0;i<shapes.size();i++){ ((myRectangle)
		 * shapes.get(i)).draw(g); }
		 */
	}

	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public void set3DColor(Color bg, Color fg) {
		// TODO Auto-generated method stub
		this.bg = bg;
		this.fg = fg;
	}

	public void setFilled(boolean filled, boolean draw3D) {
		this.filled = filled;
		this.draw3D = draw3D;
	}

	public boolean isSelected(int x, int y) {
		Point firstClick = new Point(x, y);
		Rectangle2D bigger = myRect.getBounds2D();
		bigger.setRect(bigger.getX() - 3, bigger.getY() - 3,
				bigger.getWidth() + 6, bigger.getHeight() + 6);

		Rectangle2D smaller = myRect.getBounds2D();
		smaller.setRect(smaller.getX() + 3, smaller.getY() + 3,
				smaller.getWidth() - 6, smaller.getHeight() - 6);
		if (filled == false) {

			if (bigger.contains(firstClick) && !smaller.contains(firstClick)) {
				System.out.println("true ");
				return true;

			} else {
				return false;
			}
		} else {
			if (smaller.contains(firstClick)) {

				return true;
			} else {

				return false;
			}
		}

	}

	@Override
	public void setPoints(Point start, Point end) {
		// TODO Auto-generated method stub
		
		x1 = start.x;
		x2 = end.x;
		y1 = start.y;
		y2 = end.y;

		x = Math.min(x1, x2);
		y = Math.min(y1, y2);
		// x += dx;
		// y += dy;
		// Gets the difference between the coordinates and

		width = Math.abs(x1 - x2);
		height = Math.abs(y1 - y2);
	}

	public void move(int dx, int dy) {
		// this.dx = dx;
		// this.dy = dy;
		this.start.x += dx;
		this.x = this.start.x;
		this.start.y += dy;
		this.y = this.start.y;
		// this.start.x=dx;
		// this.start.y=dy;
	}

	public int getShapeX1() {
		return this.start.x;
	}

	public int getShapeY1() {
		return this.start.y;
	}

}
