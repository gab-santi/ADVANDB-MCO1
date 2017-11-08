package View;

import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Model.DBConnect;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Query6 extends JPanel {
	private JPanel contentPane;
	private JTable table;
	private JTextField txtTitle;
	/**
	 * Create the panel.
	 */
	public Query6(DBConnect db) {
		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);
		
		
		JLabel lblOnetableQuery = new JLabel("<html>Three-Table Query | Query 2 | Search book by Title (With Full Info)\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 511, 35);
		contentPane.add(lblOnetableQuery);
		
		JLabel lblThisQueryChecks = new JLabel("<html>This query is for returning Books with the given input present in their title, with all information present.");
		lblThisQueryChecks.setBounds(10, 57, 484, 28);
		contentPane.add(lblThisQueryChecks);
		
		JLabel lblPickADate = new JLabel("Title:");
		lblPickADate.setBounds(10, 96, 71, 14);
		contentPane.add(lblPickADate);
		
		Object[][] data = 
			{
					{"test", "test", "test", "test"},
			};
		String[] columnNames = {"Title", "Author", "PublisherName", "Address"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames){

			@Override
			public String getColumnName(int arg0) {
				// TODO Auto-generated method stub
				return columnNames[arg0];
			}
			
		};
		
		JLabel lblResults = new JLabel("Results:");
		lblResults.setBounds(213, 96, 46, 14);
		contentPane.add(lblResults);
		
		JLabel lblExecutionTime = new JLabel("Execution time:");
		lblExecutionTime.setBounds(10, 354, 182, 19);
		contentPane.add(lblExecutionTime);
		
		JLabel lblSecs = new JLabel("0.0000 secs");
		lblSecs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSecs.setBounds(10, 368, 164, 31);
		contentPane.add(lblSecs);
		
		JRadioButton rdbtnOptimized = new JRadioButton("Optimized");
		rdbtnOptimized.setBounds(10, 280, 109, 23);
		contentPane.add(rdbtnOptimized);
		
		JRadioButton rdbtnUnoptimized = new JRadioButton("Unoptimized");
		rdbtnUnoptimized.setBounds(10, 306, 109, 23);
		contentPane.add(rdbtnUnoptimized);
		rdbtnUnoptimized.setSelected(true);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnUnoptimized);
		group.add(rdbtnOptimized);
		
		table = new JTable(model);
		table.setBounds(213, 114, 337, 354);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 114, 337, 354);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		
		txtTitle = new JTextField();
		txtTitle.setText("Title");
		txtTitle.setBounds(10, 112, 133, 28);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO action
			}
		});
		btnSearch.setBounds(10, 151, 133, 28);
		contentPane.add(btnSearch);

	}

}
