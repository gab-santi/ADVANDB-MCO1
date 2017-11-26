package View;

import Model.Book;
import Model.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Query4 extends JPanel {
	private JPanel contentPane;
	private JTable table;
	private JTextField txtAuthor;

	/**
	 * Create the panel.
	 */
	public Query4(DBConnect db) {
		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);


		JLabel lblOnetableQuery = new JLabel("<html>Two-Table Query | Query 2 | Search Books by Author\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 511, 35);
		contentPane.add(lblOnetableQuery);

		JLabel lblThisQueryChecks = new JLabel("<html>This query is for searching all the Books written by a certain Author.");
		lblThisQueryChecks.setBounds(10, 57, 484, 28);
		contentPane.add(lblThisQueryChecks);

		JLabel lblPickADate = new JLabel("Author name:");
		lblPickADate.setBounds(10, 96, 71, 14);
		contentPane.add(lblPickADate);

		Object[][] data =
				{
						{"test", "test", "test"},
				};
		String[] columnNames = {"Title", "PublisherName", "AuthorName"};

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
		rdbtnUnoptimized.setBounds(10, 200, 109, 23);
		contentPane.add(rdbtnUnoptimized);
		rdbtnUnoptimized.setSelected(true);

		JRadioButton rdbtnInnerJoin = new JRadioButton("Inner Join");
		rdbtnInnerJoin.setActionCommand("Inner Join");
		rdbtnInnerJoin.setBounds(10, 226, 109, 23);
		contentPane.add(rdbtnInnerJoin);

		JRadioButton rdbtnInnerJoinWithView = new JRadioButton("Inner Join + View");
		rdbtnInnerJoinWithView.setActionCommand("Inner Join + View");
		rdbtnInnerJoinWithView.setBounds(10, 252, 109, 23);
		contentPane.add(rdbtnInnerJoinWithView);

		JRadioButton rdbtnInnerJoinTempTable = new JRadioButton("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setActionCommand("Inner Join + Temporary Table");
		rdbtnInnerJoinTempTable.setBounds(10, 278, 182, 23);
		contentPane.add(rdbtnInnerJoinTempTable);

		JRadioButton rdbtnInnerJoinSingleIndex = new JRadioButton("Inner Join + Single Index");
		rdbtnInnerJoinSingleIndex.setActionCommand("Inner Join + Single Index");
		rdbtnInnerJoinSingleIndex.setBounds(10, 304, 182, 23);
		contentPane.add(rdbtnInnerJoinSingleIndex);

		JRadioButton rdbtnInnerJoinCompositeIndex = new JRadioButton("Inner Join + Composite Index");
		rdbtnInnerJoinCompositeIndex.setActionCommand("Inner Join + Composite Index");
		rdbtnInnerJoinCompositeIndex.setBounds(10, 330, 182, 23);
		contentPane.add(rdbtnInnerJoinCompositeIndex);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnUnoptimized);
		group.add(rdbtnInnerJoin);
		group.add(rdbtnInnerJoinWithView);
		group.add(rdbtnInnerJoinTempTable);
		group.add(rdbtnInnerJoinSingleIndex);
		group.add(rdbtnInnerJoinCompositeIndex);

		table = new JTable(model);
		table.setBounds(213, 114, 337, 354);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 114, 337, 354);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);

		txtAuthor = new JTextField();
		txtAuthor.setText("Author\r\n");
		txtAuthor.setBounds(10, 112, 133, 28);
		contentPane.add(txtAuthor);
		txtAuthor.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);

				switch (group.getSelection().getActionCommand()) {
					case "Inner Join + View":
						db.preQuery4_3(txtAuthor.getText());
						break;
					case "Inner Join + Temporary Table":
						db.preQuery4_4(txtAuthor.getText());
						break;
					case "Inner Join + Single Index":
						db.preQuery4_5();
						break;
					case "Inner Join + Composite Index":
						db.preQuery4_6();
						break;
				}

				db.enableProfiling();
				for (int i = 0; i < 14; i++) {
					switch (group.getSelection().getActionCommand()) {
						case "Natural Join":
							db.query4_1(txtAuthor.getText());
							break;
						case "Inner Join + View":
							db.query4_3();
							break;
						case "Inner Join + Temporary Table":
							db.query4_4();
							break;
						default:
							db.query4_2(txtAuthor.getText());
					}
				}
				ArrayList<Book> b;
				switch (group.getSelection().getActionCommand()) {
					case "Natural Join":
						b = db.query4_1(txtAuthor.getText());
						break;
					case "Inner Join + View":
						b = db.query4_3();
						break;
					case "Inner Join + Temporary Table":
						b = db.query4_4();
						break;
					default:
						b = db.query4_2(txtAuthor.getText());
				}

				double time = db.getTime();

				switch (group.getSelection().getActionCommand()) {
					case "Inner Join + View":
						db.postQuery4_3();
						break;
					case "Inner Join + Temporary Table":
						db.postQuery4_4();
						break;
					case "Inner Join + Single Index":
						db.postQuery4_5();
						break;
					case "Inner Join + Composite Index":
						db.postQuery4_6();
						break;
				}

				lblSecs.setText(String.format("%.6f secs", time));

				for (int i = 0; i < b.size(); i++) {
					String title = b.get(i).getTitle();
					String publisherName = b.get(i).getPublisherName();
					String authorFullName = b.get(i).getAuthorFullName();

					Object[] data = {title, publisherName, authorFullName};

					model.addRow(data);
				}

			}
		});
		btnSearch.setBounds(10, 151, 133, 28);
		contentPane.add(btnSearch);
	}

}
