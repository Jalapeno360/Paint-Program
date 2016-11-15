import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class Painter extends JPanel {
	private Point start;
	private Point end;
	private JLabel status = new JLabel();
	private String[] colorName = { "Black", "Red", "Blue", "Green" };
	private JComboBox<String> colorList = new JComboBox<String>(colorName);
	private String[] shapesName = { "Rectangle", "Oval", "Line" };
	private JComboBox<String> shapeList = new JComboBox<String>(shapesName);
	private JPanel tools = new JPanel();
	private Button undo = new Button("Undo");
	private Button clear = new Button("Clear");
	private JCheckBox filled = new JCheckBox("filled");
	private JCheckBox shape3D = new JCheckBox("3D filled");
	private JPanel statusBar = new JPanel();
	private myShape myShape;
	private String current = "Rectangle";
	Shape currentShape;
	Shape prevShape;
	ArrayList<myShape> shapes = new ArrayList<myShape>();
	private Color pickedColor;
	private boolean boolFill = false;
	private boolean drag = false;
	private Point prePoint;
	private Point newClick;
	private Point click;
	private boolean draw3D = false; 
	 Color bg;
     Color fg; 
	myShape shapeBeingDragged = null; // This is null unless a shape is being
										// dragged.
										// A non-null value is used as a signal
										// that dragging
										// is in progress, as well as indicating
										// which shape
										// is being dragged.

	int prevDragX; // During dragging, these record the x and y coordinates of
					// the
	int prevDragY; // previous position of the mouse.

	public Painter() {
		super();
		handleMouse h = new handleMouse();
		addMouseListener(h);
		addMouseMotionListener(h);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 1000));
		setBackground(Color.WHITE);

		statusBar.setLayout(new BorderLayout());
		add(statusBar, BorderLayout.SOUTH);
		statusBar.add(status, BorderLayout.WEST);
		add(tools, BorderLayout.NORTH);
		statusBar.setVisible(true);
		tools.add(undo);
		tools.add(clear);
		tools.add(colorList);
		tools.add(shapeList);
		tools.add(filled);
		tools.add(shape3D);
		filled.setHorizontalAlignment(SwingConstants.LEFT);
		// shapeList.addActionListener(this);
		setVisible(true);
		shapeList.addActionListener(h);
		colorList.addActionListener(h);
		undo.addActionListener(h);
		clear.addActionListener(h);
		filled.addActionListener(h);
		shape3D.addActionListener(h);
		// handler.setColor(pickedColor);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,

				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(3));

         bg = getBackground();
         fg = getForeground();

		draw();
		if (end != null) {
			for (int i = 0; i < shapes.size(); i++) {

				shapes.get(i).draw(g);

			}
		}
		repaint();
		/*
		 * for(int i = 0; i<shapes.size();i++){
		 * System.out.println(shapes.get(i)); }
		 */

	}

	void draw() {
		if (start != null) {

			myShape.setFilled(boolFill, draw3D);
			myShape.setColor(pickedColor);
			myShape.set3DColor(bg,fg);
			myShape.setPoints(start, end);

		}
	}

	private class handleMouse extends MouseAdapter
			implements MouseMotionListener, ActionListener {
		// myShape handle;

		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)
					&& (shapeBeingDragged != null)) {

				if (drag) {

					// shapeBeingDragged.move(dx,dy);
					shapeBeingDragged.move(
							e.getX() - shapeBeingDragged.getShapeX1(),
							e.getY() - shapeBeingDragged.getShapeY1());
					// dx = 0;
					// dy = 0;
					repaint();

				}
			} else {
				end = e.getPoint();
			}
			repaint();

		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {
			status.setText("Mouse outside frame");

		}

		public void mouseMoved(MouseEvent e) {
			status.setText(String.format("(%d,%d)", e.getX(), e.getY()));

		}

		public void mousePressed(MouseEvent e) {

			// logic for contains method to be true when clicked appropriately
			if (SwingUtilities.isRightMouseButton(e)) {
				click = e.getPoint();
				for (int i = shapes.size() - 1; i >= 0; i--) {

					if (shapes.get(i) instanceof Rectangle
							|| shapes.get(i) instanceof Ellipse2D.Float
							|| shapes.get(i) instanceof Line2D.Double) {

						if (shapes.get(i).isSelected(click.x,
								click.y) == true) {
							System.out.println("point is selected");

							drag = shapes.get(i).isSelected(click.x, click.y);
							if (drag == true) {
								shapeBeingDragged = shapes.get(i);
								System.out.println("hello");
							}
						}
					} // to
						// show
						// shape
						// in
					// front of other shapes
				}

			}

			if (SwingUtilities.isLeftMouseButton(e))

			{
				switch (current) {

				case "Rectangle":

					myShape = new myRectangle();

					break;

				case "Line":

					myShape = new myLine();

					break;
				case "Oval":

					myShape = new myOval();

					break;

				}
				start = e.getPoint();
				end = start;

				shapes.add(myShape);

			}

		}

		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				drag = false;
				shapeBeingDragged = null;

			} else {
				end = e.getPoint();
				start = null;
				repaint();

			}
		}

		private void undo() {

			shapes.remove(shapes.size() - 1);
			repaint();

		}

		private void clear() {
			shapes.clear();
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == shapeList) {
				current = shapeList.getSelectedItem().toString();
			} else if (e.getSource() == colorList) {
				String colorSelect = colorList.getSelectedItem().toString();
				System.out.println(colorSelect);
				switch (colorSelect) {
				case "Green":
					pickedColor = Color.GREEN;
					fg= Color.GREEN;
					bg = Color.WHITE;
					System.out.println("bg = green");
					break;
				case "Red":
					pickedColor = Color.RED;
					fg = Color.RED;
					bg = Color.WHITE;
					break;
				case "Blue":
					pickedColor = Color.BLUE;
					fg= Color.BLUE;
					bg = Color.WHITE;
					break;

				case "Black":
					pickedColor = Color.BLACK;
					fg = Color.BLACK;
					bg = Color.WHITE;
					break;
				}

			}
			if (e.getSource() == undo && (shapes.isEmpty() == false)) {
				undo();
				repaint();
			}
			if (e.getSource() == clear && (shapes.isEmpty() == false)) {
				clear();
				repaint();
			}
			if (e.getSource() == filled) {

				if (filled.isSelected()) {

					boolFill = true;
					// System.out.println(boolFill);
				} else {
					boolFill = false;
					// System.out.println(boolFill);

				}
			}
			if(e.getSource() == shape3D){
				draw3D = true;
				System.out.println("true");
			}else{
				draw3D = false; 
			}
		}

	}

	public static void main(String args[]) {
		Painter panel = new Painter();
		JFrame window = new JFrame("Paint");
		// myShape program = new myShape();
		// window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		// panel.repaint();
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
	}

}
