import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class myOval extends Ellipse2D.Float implements myShape {

	private Color color;
	private boolean filled = false;
	
	 private int width;
	 private int height;
	private int x1,x2,y1,y2;
	public myOval() {

	}

	public void draw(Graphics g) {
		
		g.setColor(color);
		if (filled == false) {
			g.drawOval(x1, y1, width, height);
		} else {
			g.fillOval(x1, y1, width, height);
		}
		

	}

	/*
	 * @Override public void setColor(Color color) { // TODO Auto-generated
	 * method stub this.color = color; }
	 */
	@Override
	public void setFilled(boolean filled, boolean draw3D) {
		// TODO Auto-generated method stub
		this.filled = filled;
	}

	@Override
	public void set3DColor(Color bg, Color fg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected(int x, int y) {
		Point clicked = new Point(x, y);
		Ellipse2D big = new Ellipse2D.Float(x1 - 1, y1 - 1, width + 3,
				height + 3);
		Ellipse2D small = new Ellipse2D.Float(x1, y1, width - 3,
				height - 3);

		if (filled == true) {
			if (small.contains(clicked)) {
				return true;
			}
		} else if (big.contains(clicked) && !small.contains(clicked)) {
			// System.out.println("true");
			return true;
		} else {
			// System.out.println("False");
		}
		return false;

	}

	@Override
	public void move(int dx, int dy) {
		// TODO Auto-generated method stub
		this.x1 += dx;
		this.y1 += dy;
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public void setPoints(Point start, Point end) {
		// TODO Auto-generated method stub
	
		this.x1 = start.x;
		 this.x2 = end.x;
		 this.y1 = start.y;
		this.y2 = end.y;
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.width = Math.abs(x1 - x2);
		this.height = Math.abs(y1 - y2);
		
	}

	@Override
	public int getShapeX1() {
		return this.x1;
	}

	@Override
	public int getShapeY1() {
		// TODO Auto-generated method stub
		return this.y1;
	}

}
