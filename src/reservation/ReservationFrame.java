package reservation;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sun.rowset.*;

import dbconnection.MakeConnection;
import model.ReservationModel;

public class ReservationFrame extends JFrame implements RowSetListener {

	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelReservationNumber;
	JLabel labelCruiseNumber;
	JLabel labelDateOfReservation;
	JLabel labelDatePaid;

	JTextField textFieldReservationNumber;
	JTextField textFieldCruiseNumber;
	JTextField textFieldDateOfReservation;
	JTextField textFieldDatePaid;

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;

	ReservationModel reservationModel;

	public ReservationFrame() throws SQLException {

		// get the connection
		connection = MakeConnection.getConnection("SM");

		// event listen when window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Window is closed..");
				try {
					connection.close();
				} catch (SQLException ex) {
					printSQLException(ex);
				}
				System.exit(0);
			}
		});

		// create the frame components
		CachedRowSet myCachedRowSet = getContentsOfReservationTable();
		reservationModel = new ReservationModel(myCachedRowSet);
		reservationModel.addEventHandlersToRowSet(this);

		table = new JTable(); // Displays the table
		table.setModel(reservationModel);

		labelReservationNumber = new JLabel();
		labelCruiseNumber = new JLabel();
		labelDateOfReservation = new JLabel();
		labelDatePaid = new JLabel();

		textFieldReservationNumber = new JTextField(10);
		textFieldCruiseNumber = new JTextField(10);
		textFieldDateOfReservation = new JTextField(10);
		textFieldDatePaid = new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();

		labelReservationNumber.setText("PostID:");
		labelCruiseNumber.setText("Content:");
		labelDateOfReservation.setText("DatePosted");
		//labelDatePaid.setText("DatePaid");

		textFieldReservationNumber.setText("Enter Post OD here");
		textFieldCruiseNumber.setText("test");
		textFieldDateOfReservation.setText("12/30/2000");
		textFieldDatePaid.setText("12/30/2000");

		button_ADD_ROW.setText("Add row to table");
		button_UPDATE_DATABASE.setText("Update database");
		button_DISCARD_CHANGES.setText("Discard changes");

		Container contentPane = getContentPane();
		contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		contentPane.add(new JScrollPane(table), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(labelReservationNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(textFieldReservationNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelCruiseNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldCruiseNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelDateOfReservation, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textFieldDateOfReservation, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labelDatePaid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textFieldDatePaid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_ADD_ROW, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_DATABASE, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		contentPane.add(button_DISCARD_CHANGES, c);

		button_ADD_ROW.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(ReservationFrame.this,
						new String[] { "Adding the following row:",
								"PostID: [" + textFieldReservationNumber.getText() + "]",
								"Content: [" + textFieldCruiseNumber.getText() + "]",
								"DateOfReservation: [" + textFieldDateOfReservation.getText() + "]",
								"DatePaid: [" + textFieldDatePaid.getText() + "]" });

				try {

					reservationModel.insertRow(Integer.parseInt(textFieldReservationNumber.getText().trim()),
							textFieldCruiseNumber.getText().trim(),
							textFieldDateOfReservation.getText().trim());
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}

		});

		button_UPDATE_DATABASE.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					reservationModel.reservationRowSet.acceptChanges();
					// Update customer table as well
					// Assuming customer id is 10000082
					// This section can be improvised to bring dynamic customer
					// id.
					updateCustomerReservationTable(10000082,
							Integer.parseInt(textFieldReservationNumber.getText().trim()));

				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
					// Now revert back changes
					try {
						createNewTableModel();
					} catch (SQLException sqle2) {
						displaySQLExceptionDialog(sqle2);
					}
				}
			}

		});

		button_DISCARD_CHANGES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createNewTableModel();
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}
		});

	}

	private void createNewTableModel() throws SQLException {
		reservationModel = new ReservationModel(getContentsOfReservationTable());
		reservationModel.addEventHandlersToRowSet(this);
		table.setModel(reservationModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(ReservationFrame.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfReservationTable() {

		// write code to fetch the records from reservation table
		// setting for scroll option

		CachedRowSet crs = null;
		try {
			connection = MakeConnection.getConnection("OC");
			crs = new CachedRowSetImpl();
			crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			crs.setUsername("root");
			crs.setPassword("root");

			// In MySQL, to disable auto-commit, set the property
			// relaxAutoCommit to
			// true in the connection URL.

			crs.setUrl("jdbc:mysql://localhost:3306/OC?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand("select reservationNo, cruiseNo, dateReservationMade, datePaid from reservation");
			crs.execute();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return crs;

	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException) e).getSQLState());
					System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
					System.err.println("Message: " + e.getMessage());
					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}

	private static boolean ignoreSQLException(String sqlState) {
		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}
		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;
		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;
		return false;
	}

	private void updateCustomerReservationTable(int customerId, int reservationId) {

		String insertIntoCustomerReservationQuery = "INSERT INTO customerReservation VALUES " + "(?, ?);";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setInt(1, customerId);
			pst.setInt(2, reservationId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		try {
			ReservationFrame reservationFrame = new ReservationFrame();
			reservationFrame.setTitle("Reservation");
			reservationFrame.setSize(600, 600);
			reservationFrame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rowSetChanged(RowSetEvent event) {

	}

	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.reservationModel.reservationRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			reservationModel = new ReservationModel(reservationModel.getCoffeesRowSet());
			table.setModel(reservationModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(ReservationFrame.this, new String[] { // Display
																				// a
																				// 2-line
																				// message
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	public void cursorMoved(RowSetEvent event) {

	}

}
