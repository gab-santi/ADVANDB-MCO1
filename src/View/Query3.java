package View;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

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

public class Query3 extends JPanel {
	private JPanel contentPane;
	private JTable table;
	private JTextField txtAuthor;
	/**
	 * Create the panel.
	 */
	public Query3(DBConnect db) {
		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);
		
		
		JLabel lblOnetableQuery = new JLabel("<html>Two-Table Query | Query 1 | Number of Books of Author\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 511, 35);
		contentPane.add(lblOnetableQuery);
		
		JLabel lblThisQueryChecks = new JLabel("<html>This query is for returning the number of books a certain author has published.");
		lblThisQueryChecks.setBounds(10, 57, 484, 28);
		contentPane.add(lblThisQueryChecks);
		
		JLabel lblPickADate = new JLabel("Author name:");
		lblPickADate.setBounds(10, 96, 71, 14);
		contentPane.add(lblPickADate);
		
		Object[][] data = 
			{
					{"test"},
			};
		String[] columnNames = {"NoBksWr"};
		
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
		
		txtAuthor = new JTextField();
		txtAuthor.setText("Author\r\n");
		txtAuthor.setBounds(10, 112, 133, 28);
		contentPane.add(txtAuthor);
		txtAuthor.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DecimalFormat df = new DecimalFormat("#.####");
				model.setRowCount(0);
				long beforeTime = System.currentTimeMillis();
				int c = db.query3(txtAuthor.getText());
				long time = System.currentTimeMillis();
				time -= beforeTime;
				
				lblSecs.setText(df.format(time * 0.0001) + " secs");
				Object[] data = {c};
				
				model.addRow(data);
				
			
			}
		});
		btnSearch.setBounds(10, 151, 133, 28);
		contentPane.add(btnSearch);

	}

}
