package View;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Model.DBConnect;

public class ThreeTables extends JPanel {

	/**
	 * Create the panel.
	 */
	public ThreeTables(JPanel mainPanel, CardLayout cl, DBConnect db) {
		setBounds(0, 0, 640, 480);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton button = new JButton("< Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(mainPanel, "home");
			}
		});
		button.setVerticalAlignment(SwingConstants.TOP);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 1;
		gbc_button.gridy = 1;
		add(button, gbc_button);
		
		JPanel query1 = new JPanel();
		JPanel query2 = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Query 1", new Query5(db));
		tabbedPane.addTab("Query 2", new Query6(db));
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 2;
		add(tabbedPane, gbc_tabbedPane);
		
		
	}

}
