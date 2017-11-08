package View;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public HomePanel(JPanel mainPanel, CardLayout cl) {
		setBounds(0, 0, 640, 480);
		setLayout(null);
		
		JLabel lblAdvandbMco = new JLabel("ADVANDB MCO1 - Query Optimization");
		lblAdvandbMco.setBounds(60, 60, 503, 37);
		lblAdvandbMco.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdvandbMco.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblAdvandbMco);
		
		JLabel lblSGroup = new JLabel("S18 Group 11 - Santiago, Embestro, San Pedro");
		lblSGroup.setBounds(199, 132, 225, 14);
		lblSGroup.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSGroup);
		
		JButton btnOneTable = new JButton("1 Table");
		btnOneTable.setBounds(278, 271, 67, 23);
		btnOneTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(mainPanel, "one table");
			}
		});
		add(btnOneTable);
		
		JButton btnTwoTables = new JButton("2 Tables");
		btnTwoTables.setBounds(275, 299, 73, 23);
		btnTwoTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(mainPanel, "two tables");
			}
		});
		add(btnTwoTables);
		
		JButton btnThreeTables = new JButton("3 Tables");
		btnThreeTables.setBounds(275, 327, 73, 23);
		btnThreeTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(mainPanel, "three tables");
			}
		});
		add(btnThreeTables);
		
		JButton btnFourTables = new JButton("4-6 Tables");
		btnFourTables.setBounds(270, 355, 83, 23);
		btnFourTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(mainPanel, "four tables");
			}
		});
		add(btnFourTables);
	}

}
