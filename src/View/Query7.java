package View;

import Model.Book;
import Model.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Query7 extends JPanel {
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the panel.
	 */

	public Query7(DBConnect db) {
		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);


		JLabel lblOnetableQuery = new JLabel("<html>Four-Table Query | Query 1 | Most Borrowed Books Ever For One Branch Using Branch Name\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 511, 35);
		contentPane.add(lblOnetableQuery);

		JLabel lblThisQueryChecks = new JLabel("<html>This query is for knowing the most borrowed Books per Branch by searching for the Title.");
		lblThisQueryChecks.setBounds(10, 57, 484, 28);
		contentPane.add(lblThisQueryChecks);

		JLabel lblPickADate = new JLabel("Branch name:");
		lblPickADate.setBounds(10, 96, 71, 14);
		contentPane.add(lblPickADate);

		Object[][] data =
				{
						{"test", "test", "test", "test", "test"},
				};
		String[] columnNames = {"BorrowCountCol", "Title", "AuthorFirstName", "AuthorLastName", "PublisherName"};

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

		JRadioButton rdbtnInnerJoin = new JRadioButton("Inner Join");
		rdbtnInnerJoin.setActionCommand("Inner Join");
		rdbtnInnerJoin.setBounds(10, 243, 109, 23);
		contentPane.add(rdbtnInnerJoin);

		JRadioButton rdbtnInnerJoinWithView = new JRadioButton("Inner Join + View");
		rdbtnInnerJoinWithView.setActionCommand("Inner Join + View");
		rdbtnInnerJoinWithView.setBounds(10, 269, 109, 23);
		contentPane.add(rdbtnInnerJoinWithView);

		JRadioButton rdbtnInnerJoinTempTable = new JRadioButton("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setActionCommand("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setBounds(10, 295, 182, 23);
		contentPane.add(rdbtnInnerJoinTempTable);

		JRadioButton rdbtnInnerJoinSingleIndex = new JRadioButton("Inner Join + Single Index");
		rdbtnInnerJoinSingleIndex.setActionCommand("Inner Join + Single Index");
		rdbtnInnerJoinSingleIndex.setBounds(10, 321, 182, 23);
		contentPane.add(rdbtnInnerJoinSingleIndex);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnUnoptimized);
		group.add(rdbtnInnerJoin);
		group.add(rdbtnInnerJoinWithView);
		group.add(rdbtnInnerJoinTempTable);
		group.add(rdbtnInnerJoinSingleIndex);

		table = new JTable(model);
		table.setBounds(213, 114, 337, 354);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 114, 337, 354);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);

		JComboBox comboBoxBranchName = new JComboBox();
		comboBoxBranchName.setBounds(10, 121, 133, 19);
		ArrayList<String> items = db.getAllBranchNames();
		for (String s : items)
			comboBoxBranchName.addItem(s);

		contentPane.add(comboBoxBranchName);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO action
				model.setRowCount(0);
				String branch = comboBoxBranchName.getSelectedItem().toString();
				System.out.println(branch);

				switch (group.getSelection().getActionCommand()) {
					case "Inner Join + View":
						db.preQuery7_3(branch);
						break;
					case "Inner Join + Temporary Table":
						db.preQuery7_4(branch);
						break;
					case "Inner Join + Single Index":
						db.preQuery7_5();
						break;
				}

				db.enableProfiling();
				for (int i = 0; i < 14; i++) {
					switch (group.getSelection().getActionCommand()) {
						case "Natural Join":
							db.query7_1(branch);
							break;
						case "Inner Join + View":
							db.query7_3();
							break;
						case "Inner Join + Temporary Table":
							db.query7_4();
							break;
						default:
							db.query7_2(branch);
							break;
					}
				}
				ArrayList<Book> b;

				switch (group.getSelection().getActionCommand()) {
					case "Natural Join":
						b = db.query7_1(branch);
						break;
					case "Inner Join + View":
						b = db.query7_3();
						break;
					case "Inner Join + Temporary Table":
						b = db.query7_4();
						break;
					default:
						b = db.query7_2(branch);
						break;
				}

				double time = db.getTime();

				switch (group.getSelection().getActionCommand()) {
					case "Inner Join + View":
						db.postQuery7_3();
						break;
					case "Inner Join + Temporary Table":
						db.postQuery7_4();
						break;
					case "Inner Join + Single Index":
						db.postQuery7_5();
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
