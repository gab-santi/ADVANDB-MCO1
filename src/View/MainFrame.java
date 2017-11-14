package View;

import Model.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private CardLayout cl;
	private DBConnect db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, "Unable to get look and feel from system.");
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		db = new DBConnect();
		cl = new CardLayout();
		setTitle("ADVANDB MCO1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(cl);

		setContentPane(contentPane);
		contentPane.add(new HomePanel(contentPane, cl), "home");
		contentPane.add(new OneTable(contentPane, cl, db.getInstance()), "one table");
		contentPane.add(new TwoTables(contentPane, cl, db.getInstance()), "two tables");
		contentPane.add(new ThreeTables(contentPane, cl, db.getInstance()), "three tables");
		contentPane.add(new FourTables(contentPane, cl, db.getInstance()), "four tables");
		cl.show(contentPane, "home");
	}

	public CardLayout getLayout() {
		return this.cl;
	}

}
