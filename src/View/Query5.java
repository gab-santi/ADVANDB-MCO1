package View;

import Model.Book;
import Model.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Query5 extends JPanel {
	private JPanel contentPane;
	private JTable table;
	private JTextField txtBranchID;

	/**
	 * Create the panel.
	 */
	public Query5(DBConnect db) {
		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);


		JLabel lblOnetableQuery = new JLabel("<html>Three-Table Query | Query 1 | Most borrowed Books per BranchID\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 511, 35);
		contentPane.add(lblOnetableQuery);

		JLabel lblThisQueryChecks = new JLabel("<html>This query is for knowing the most borrowed Books per Branch by searching for the BranchID.");
		lblThisQueryChecks.setBounds(10, 57, 484, 28);
		contentPane.add(lblThisQueryChecks);

		JLabel lblPickADate = new JLabel("BranchID:");
		lblPickADate.setBounds(10, 96, 71, 14);
		contentPane.add(lblPickADate);

		Object[][] data =
				{
						{"test", "test", "test", "test", "test"},
				};
		String[] columnNames = {"BorrowCount", "Title", "AuthorFirstName", "AuthorLastName", "PublisherName"};

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {

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

		JRadioButton rdbtnOptimized = new JRadioButton("InnerJoin");
		rdbtnOptimized.setBounds(10, 243, 109, 23);
		contentPane.add(rdbtnOptimized);

		JRadioButton rdbtnUnoptimized = new JRadioButton("Unoptimized");
		rdbtnUnoptimized.setBounds(10, 217, 109, 23);
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

		txtBranchID = new JTextField();
		txtBranchID.setBounds(10, 112, 133, 28);
		contentPane.add(txtBranchID);
		txtBranchID.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO action
				model.setRowCount(0);

				db.enableProfiling();
				for (int i = 0; i < 14; i++) {
					db.query5(Integer.parseInt(txtBranchID.getText()));
				}
				ArrayList<Book> b = db.query5(Integer.parseInt(txtBranchID.getText()));
				double time = db.getTime();
				lblSecs.setText(String.format("%.6f secs", time));

				for (int i = 0; i < b.size(); i++) {
					int borrowCount = b.get(i).getBorrowCount();
					String title = b.get(i).getTitle();
					String authorFirstName = b.get(i).getAuthorFirstName();
					String authorLastName = b.get(i).getAuthorLastName();
					String publisherName = b.get(i).getPublisherName();

					Object[] data = {borrowCount, title, authorFirstName, authorLastName, publisherName};

					model.addRow(data);
				}
			}
		});
		btnSearch.setBounds(10, 151, 133, 28);
		contentPane.add(btnSearch);
		
		JRadioButton rdbtnInnerJoinWithView = new JRadioButton("Inner Join + View");
		rdbtnInnerJoinWithView.setBounds(10, 269, 109, 23);
		contentPane.add(rdbtnInnerJoinWithView);
		group.add(rdbtnInnerJoinWithView);
		
		JRadioButton rdbtnInnerJoinTempTable = new JRadioButton("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setBounds(10, 295, 182, 23);
		contentPane.add(rdbtnInnerJoinTempTable);
		
		JRadioButton rdbtnInnerJoinSingleIndex = new JRadioButton("Inner Join + Single Index");
		rdbtnInnerJoinSingleIndex.setBounds(10, 321, 182, 23);
		contentPane.add(rdbtnInnerJoinSingleIndex);
		
		group.add(rdbtnInnerJoinWithView);
		group.add(rdbtnInnerJoinSingleIndex);
		group.add(rdbtnInnerJoinSingleIndex);
	}

}
