package View;

import Model.Book;
import Model.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnUnoptimized);
		group.add(rdbtnInnerJoin);

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
				model.setRowCount(0);

				db.enableProfiling();
				for (int i = 0; i < 14; i++) {
					switch (group.getSelection().getActionCommand()) {
						case "Natural Join":
							db.query6_1(txtTitle.getText());
							break;
						case "Inner Join":
							db.query6_2(txtTitle.getText());
							break;
					}
				}
				ArrayList<Book> b = null;

				switch (group.getSelection().getActionCommand()) {
					case "Natural Join":
						b = db.query6_1(txtTitle.getText());
						break;
					case "Inner Join":
						b = db.query6_2(txtTitle.getText());
						break;
				}

				double time = db.getTime();
				lblSecs.setText(String.format("%.6f secs", time));

				for (int i = 0; i < b.size(); i++) {
					String title = b.get(i).getTitle();
					String author = b.get(i).getAuthorFullName();
					String publisherName = b.get(i).getPublisherName();
					String address = b.get(i).getPublisherAddress();

					Object[] data = {title, author, publisherName, address};

					model.addRow(data);
				}
			}
		});
		btnSearch.setBounds(10, 151, 133, 28);
		contentPane.add(btnSearch);
	}

}
