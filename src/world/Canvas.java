package world;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import util.Parameters;
import cell.ACell;

public class Canvas extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4779241908273374315L;

	public Canvas() {
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;

		for (int x = 0; x < Parameters.XSize; x++) {
			for (int y = 0; y < Parameters.YSize; y++) {
				g2d.setColor(Environment.getColor(x, y));
				g2d.fillRect(x * Parameters.SCALE, y * Parameters.SCALE,
						Parameters.SCALE, Parameters.SCALE);
			}
		}

		for (ACell cell : Environment.cellList) {
			int x = cell.location.width;
			int y = cell.location.height;

			g2d.setColor(cell.getColor());
			g2d.fillOval(x * Parameters.SCALE, y * Parameters.SCALE,
					Parameters.SCALE, Parameters.SCALE);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX() / Parameters.SCALE;
		int y = arg0.getY() / Parameters.SCALE;

		Environment.reportCell(x, y);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
