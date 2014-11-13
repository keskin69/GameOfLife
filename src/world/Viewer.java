package world;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import util.Parameters;

public class Viewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1057499364708996653L;
	Canvas view = null;
	JTextArea txaInfo = null;
	JTextArea txaDetail = null;

	public Viewer() {
		setTitle("Game of Life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		view = new Canvas();
		view.setPreferredSize(new Dimension(
				Parameters.XSize * Parameters.SCALE, Parameters.YSize
						* Parameters.SCALE));
		add(view, BorderLayout.CENTER);

		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(220, view.getHeight()));
		add(infoPanel, BorderLayout.LINE_END);

		txaInfo = new JTextArea();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(txaInfo, BorderLayout.NORTH);

		JPanel pnlButton = new JPanel();
		add(pnlButton, BorderLayout.PAGE_END);

		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Environment.active = !Environment.active;
			}
		});
		pnlButton.add(btnPause, BorderLayout.SOUTH);

		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Environment.checkSum();
			}
		});
		pnlButton.add(btnCheck, BorderLayout.SOUTH);

		JButton btnKill = new JButton("Kill All");
		btnKill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Environment.killAll();
			}
		});
		pnlButton.add(btnKill, BorderLayout.SOUTH);

		txaDetail = new JTextArea();
		infoPanel.add(txaDetail, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	public void showDetail(String str) {
		txaDetail.setText(str);
	}
}
