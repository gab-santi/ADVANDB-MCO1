package View;

import Model.BookLoan;
import Model.DBConnect;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Query1 extends JPanel {
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public Query1(DBConnect db) {


		setLayout(new CardLayout(0, 0));
		contentPane = new JPanel();
		add(contentPane, "name_625243518436875");
		contentPane.setLayout(null);


		JLabel lblOnetableQuery = new JLabel("One-Table Query | Query 1 | Late-returned Books\r\n");
		lblOnetableQuery.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOnetableQuery.setBounds(10, 11, 453, 28);
		contentPane.add(lblOnetableQuery);

		JLabel lblThisQueryChecks = new JLabel("<html>This query is for returning all books that have due dates equal to the user\r\n<br/>input that were returned past their due date.");
		lblThisQueryChecks.setBounds(10, 47, 354, 28);
		contentPane.add(lblThisQueryChecks);

		JLabel lblPickADate = new JLabel("Pick a date:");
		lblPickADate.setBounds(10, 96, 56, 14);
		contentPane.add(lblPickADate);

		JCalendar calendar = new JCalendar();
		calendar.setBounds(10, 114, 182, 153);
		calendar.getDayChooser().setWeekOfYearVisible(false);
		calendar.getDayChooser().setSundayForeground(Color.RED);
		calendar.getDayChooser().setWeekdayForeground(Color.GRAY);
		calendar.getDayChooser().setDecorationBackgroundColor(Color.WHITE);
		calendar.getDayChooser().getDayPanel().setBackground(Color.WHITE);
		contentPane.add(calendar);

		Object[][] data =
				{
						{"test", "test", "test", "test", "test", "test"},
				};
		String[] columnNames = {"BookID", "BranchID", "CardNo", "DateOut", "DueDate", "DateReturned"};

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

		PropertyChangeListener defaultProperty = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				model.setRowCount(0);
				ZoneId zoneId = ZoneId.of("Asia/Manila");
				ZonedDateTime zdt = ZonedDateTime.ofInstant(calendar.getDate().toInstant(), zoneId);

				//enable profiling
				db.enableProfiling();
				//run query 14 times
				for (int i = 0; i < 14; i++) {
					db.query1(zdt.toLocalDate());
				}
				//run query for the 15th time and save it for processing
				ArrayList<BookLoan> bl = db.query1(zdt.toLocalDate());
				//get the average time of the 15 queries
				double time = db.getTime();
				//print the time
				lblSecs.setText(String.format("%.6f secs", time));

				for (int i = 0; i < bl.size(); i++) {
					int bookID = bl.get(i).getBookID();
					int branchID = bl.get(i).getBranchID();
					int cardNo = bl.get(i).getCardNo();
					Date dateOut = bl.get(i).getDateOut();
					Date dueDate = bl.get(i).getDueDate();
					Date dateReturned = bl.get(i).getDateReturned();

					Object[] data = {bookID, branchID, cardNo, dateOut, dueDate, dateReturned};
					model.addRow(data);
				}
			}

		};

		calendar.getDayChooser().addPropertyChangeListener("day", defaultProperty);
	}
}
