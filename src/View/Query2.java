package View;

import Model.Book;
import Model.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Query2 extends JPanel {
	private JPanel contentPane;
	private JTable table;
	private JTextField txtTitle;

	public Query2(DBConnect db) {
		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);


		JLabel lblOnetableQuery = new JLabel("One-Table Query | Query 2 | Search Book by title\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 453, 28);
		contentPane.add(lblOnetableQuery);

		JLabel lblThisQueryChecks = new JLabel("<html>This query is for returning all books that have the given input string present in their Title.");
		lblThisQueryChecks.setBounds(10, 47, 354, 28);
		contentPane.add(lblThisQueryChecks);

		JLabel lblPickADate = new JLabel("Enter book title:");
		lblPickADate.setBounds(10, 96, 94, 14);
		contentPane.add(lblPickADate);

		Object[][] data =
				{
						{"test", "test"},
				};
		String[] columnNames = {"Title", "PublisherName"};

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
		txtTitle.setBounds(10, 114, 148, 26);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);

				db.enableProfiling();
				for (int i = 0; i < 14; i++) {
					db.query2(txtTitle.getText());
				}
				ArrayList<Book> b = db.query2(txtTitle.getText());

				double time = db.getTime();
				db.disableProfiling();

				lblSecs.setText(String.format("%.6f secs", time));

				for (int i = 0; i < b.size(); i++) {
					String title = b.get(i).getTitle();
					String publisherName = b.get(i).getPublisherName();

					Object[] data = {title, publisherName};

					model.addRow(data);
				}
			}
		});
		btnSearch.setBounds(10, 151, 148, 28);
		contentPane.add(btnSearch);
	}
}
