import java.awt.*;

public interface myShape {

	void draw(Graphics g);

	void setPoints(Point start, Point end);

	void setColor(Color color);

	void setFilled(boolean filled, boolean draw3D);

	boolean isSelected(int x, int y);

	void move(int dx, int dy);

	int getShapeX1();

	int getShapeY1();
	
	void set3DColor(Color bg, Color fg);
}
