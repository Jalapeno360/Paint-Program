import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.awt.geom.Line2D;;

public class myLine extends Line2D.Double implements myShape {

	private Color color;
	private Shape myLine;
	private static final int HIT_BOX_SIZE = 2;

	Point startPoint;
	Point endPoint;
	boolean drag = false;
	private int x1, x2, y1, y2;

	public myLine() {
		super();
	}

	public void setNewPoints(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public void setPoints(Point start, Point end) {

		this.x1 = start.x;
		this.y1 = start.y;

		this.x2 = end.x;
		this.y2 = end.y;

	}

	@Override
	public void draw(Graphics g) {
		myLine = new Line2D.Double(x1, y1, x2, y2);
		g.setColor(color);

		g.drawLine(this.x1, this.y1, this.x2, this.y2);

		// myLine = new Line2D.Float(startPoint, endPoint);

	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public void set3DColor(Color bg, Color fg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFilled(boolean filled,boolean draw3D) {

	}

	/**
	 * Returns the first line in the collection of lines that is close enough to
	 * where the user clicked, or null if no such line exists
	 *
	 */

	@Override
	public boolean isSelected(int x, int y) {
		// TODO Auto-generated method stub

		int boxX = x - HIT_BOX_SIZE / 2;
		int boxY = y - HIT_BOX_SIZE / 2;

		int width = HIT_BOX_SIZE;
		int height = HIT_BOX_SIZE;

		if (myLine.intersects(boxX, boxY, width, height)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void move(int dx, int dy) {

		setNewPoints(this.x1 + dx, this.x2 + dx, this.y1 + dy, this.y2 + dy);
	}

	public int getShapeX1() {
		return this.x1;
	}

	public int getShapeY1() {
		return this.y1;
	}

}
