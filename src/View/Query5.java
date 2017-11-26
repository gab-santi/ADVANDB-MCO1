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

		JRadioButton rdbtnUnoptimized = new JRadioButton("Natural Join");
		rdbtnUnoptimized.setActionCommand("Natural Join");
		rdbtnUnoptimized.setBounds(10, 217, 109, 23);
		contentPane.add(rdbtnUnoptimized);
		rdbtnUnoptimized.setSelected(true);

		JRadioButton rdbtnOptimized = new JRadioButton("Inner Join");
		rdbtnOptimized.setActionCommand("Inner Join");
		rdbtnOptimized.setBounds(10, 243, 109, 23);
		contentPane.add(rdbtnOptimized);

		JRadioButton rdbtnInnerJoinWithView = new JRadioButton("Inner Join + View");
		rdbtnInnerJoinWithView.setActionCommand("Inner Join + View");
		rdbtnInnerJoinWithView.setBounds(10, 269, 109, 23);
		contentPane.add(rdbtnInnerJoinWithView);

		JRadioButton rdbtnInnerJoinTempTable = new JRadioButton("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setActionCommand("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setBounds(10, 295, 182, 23);
		contentPane.add(rdbtnInnerJoinTempTable);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnUnoptimized);
		group.add(rdbtnOptimized);
		group.add(rdbtnInnerJoinWithView);
		group.add(rdbtnInnerJoinTempTable);

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

				switch (group.getSelection().getActionCommand()) {
					case "Inner Join + View":
						db.preQuery5_3(Integer.parseInt(txtBranchID.getText()));
						break;
					case "Inner Join + Temporary Table":
						db.preQuery5_4(Integer.parseInt(txtBranchID.getText()));
						break;
				}

				db.enableProfiling();
				for (int i = 0; i < 14; i++) {
					switch (group.getSelection().getActionCommand()) {
						case "Natural Join":
							db.query5_1(Integer.parseInt(txtBranchID.getText()));
							break;
						case "Inner Join":
							db.query5_2(Integer.parseInt(txtBranchID.getText()));
							break;
						case "Inner Join + View":
							db.query5_3();
							break;
						case "Inner Join + Temporary Table":
							db.query5_4();
							break;
					}
				}
				ArrayList<Book> b = null;

				switch (group.getSelection().getActionCommand()) {
					case "Natural Join":
						b = db.query5_1(Integer.parseInt(txtBranchID.getText()));
						break;
					case "Inner Join":
						b = db.query5_2(Integer.parseInt(txtBranchID.getText()));
						break;
					case "Inner Join + View":
						b = db.query5_3();
						break;
					case "Inner Join + Temporary Table":
						b = db.query5_4();
						break;
				}

				double time = db.getTime();

				switch (group.getSelection().getActionCommand()) {
					case "Inner Join + View":
						db.postQuery5_3();
						break;
					case "Inner Join + Temporary Table":
						db.postQuery5_4();
						break;
				}

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
	}

}
