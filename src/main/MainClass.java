package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconnection.MakeConnection;
import model.Customer;
import model.Ship;

public class MainClass {

	public static void main(String args[]) {

		Connection conn = null;
		conn = MakeConnection.getDafaultConnection();
		if (conn != null) {

			MainClass mainClass = new MainClass();

			// create database and tablespace

			mainClass.createTablespace(conn);

			conn = MakeConnection.getConnection("SM");

			// create tables
			// if you don't want to create users again don't uncomment
			// createUsers function and grantPermission function.

			mainClass.createTables(conn);
			//mainClass.createUsers(conn);

			// create users

			// grant permissions

			//mainClass.grantPermission(conn);
			//mainClass.insertValuesToTables(conn);
			// insert into tables

		} else {
			System.out.println("Driver is not connected");
		}
	}

	public void createTablespace(Connection conn) {

		// query
		String createDatabaseQuery = "CREATE DATABASE SM";
		String useDB = "USE SM";
		String createTablespaceCommand = "CREATE TABLESPACE SM ADD DATAFILE 'SM.ibd';";
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(createDatabaseQuery);
			statement.executeUpdate(useDB);
			// uncomment below line during first execution
			// statement.executeUpdate(createTablespaceCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// statement

	}

	public void createTables(Connection conn) {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			String createTablePostQuery = "CREATE TABLE posts (postID int(5) NOT NULL,"
					+ "Content VARCHAR(40) NOT NULL, datePosted DATE, userID int(5)"
					+ "CONSTRAINT postPK PRIMARY KEY (postID)"
					+ "CONSTRAINT ownerFK FOREIGN KEY (userID) REFERENCES user(userID));";

			statement.executeUpdate(createTablePostQuery);

			/*String createTableMessageQuery = "CREATE TABLE Message (messageNo INT(6) NOT NULL,date DATE, "
					+ "shipNO VARCHAR(12), CONSTRAINT cruisePK PRIMARY KEY (cruiseNo), "
					+ "CONSTRAINT cruiseShipFK FOREIGN KEY (shipNo) REFERENCES ship (hullID));";

			statement.executeUpdate(createTableMessageQuery);

			String createTableCabinQuery = "CREATE TABLE cabin (cabinNo INT(4) NOT NULL,shipNo VARCHAR(12) NOT NULL,"
					+ "floor INT(2), cabinPricePerNight NUMERIC(6 , 2 ), nubmerOfBeds INT(2),"
					+ "CONSTRAINT cabinPK PRIMARY KEY (cabinNo , shipNo), CONSTRAINT cabinShipFK FOREIGN KEY (shipNo) "
					+ "REFERENCES ship (hullID));";

			statement.executeUpdate(createTableCabinQuery);

			String createReservationQuery = "CREATE TABLE reservation (reservationNo INT(12) NOT NULL, "
					+ "cruiseNo INT(6) NOT NULL, dateReservationMade DATE, datePaid DATE,"
					+ "CONSTRAINT reservationPK PRIMARY KEY (reservationNo), "
					+ "CONSTRAINT reservationCruiseFK FOREIGN KEY (cruiseNo) " + "REFERENCES cruise (cruiseNo));";

			statement.executeUpdate(createReservationQuery);

			String createCabinReservationTableQuery = "CREATE TABLE cabinReservation (cabinReservationID INT(12) NOT NULL,"
					+ "cabinNo INT(4) NOT NULL,shipNo VARCHAR(12),reservationNo INT(12) NOT NULL, "
					+ "CONSTRAINT cabinResPK PRIMARY KEY (cabinReservationID), "
					+ "CONSTRAINT cabinFK FOREIGN KEY (cabinNo , shipNo) REFERENCES cabin (cabinNo , shipNo),"
					+ "CONSTRAINT cabinResKF FOREIGN KEY (reservationNo) REFERENCES reservation (reservationNo));";

			statement.executeUpdate(createCabinReservationTableQuery);

			String createCustomerTableQuery = "CREATE TABLE customer (customerNo INT(12) NOT NULL,"
					+ "firstName VARCHAR(30) NOT NULL, lastName VARCHAR(40) NOT NULL, email VARCHAR(40), "
					+ "street VARCHAR(40), city VARCHAR(40), state CHAR(2), zip CHAR(5), creditCardNo VARCHAR(16),"
					+ "CONSTRAINT customerPK PRIMARY KEY (customerNo));";

			statement.executeUpdate(createCustomerTableQuery);

			String createTablePhoneNumberQuery = "CREATE TABLE phoneNumber (phoneNumber VARCHAR(10) NOT NULL, "
					+ "customerNo INT(12) NOT NULL, type VARCHAR(12) NOT NULL, "
					+ "CONSTRAINT phonePK PRIMARY KEY (phoneNumber), "
					+ "CONSTRAINT phoneCustomerFK FOREIGN KEY (customerNo) REFERENCES customer (customerNo),"
					+ "CHECK (type IN ('Mobile' , 'Home', 'Work', 'Other')));";

			statement.executeUpdate(createTablePhoneNumberQuery);
*/
			String createUserTableQuery = "CREATE TABLE user (userID INT(5) NOT NULL,"
					+ "firstname VARCHAR(20) NOT NULL, "
					+ "lastname VARCHAR(20) NOT NULL, "
					+ "email VARCHAR(20) NOT NULL, "
					+ "CONSTRAINT userPK PRIMARY KEY (userID));";
			statement.executeUpdate(createUserTableQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*public void createUsers(Connection conn) {
		// users query

		Statement statement = null;

		try {
			statement = conn.createStatement();

			String createDbAdminUserQuery = "CREATE USER 'John'@'localhost' IDENTIFIED BY 'newpassword';";

			String grantDbAdminQuery = "GRANT ALL PRIVILEGES ON * . * TO 'John'@'localhost'";

			statement.executeUpdate(createDbAdminUserQuery);
			statement.executeUpdate(grantDbAdminQuery);

			// Creating DB designer users
			statement.executeUpdate(this.formCreateUserQuery("Tom", "Tom"));
			statement.executeUpdate(this.formCreateUserQuery("Joe", "Joe"));

			// creating cruise manager
			statement.executeUpdate(this.formCreateUserQuery("Kevin", "Kevin"));
			statement.executeUpdate(this.formCreateUserQuery("Victoria", "Victoria"));

			// create travel agent users
			statement.executeUpdate(this.formCreateUserQuery("Robert", "Robert"));
			statement.executeUpdate(this.formCreateUserQuery("Amy", "Amy"));

			// create marketing user
			statement.executeUpdate(this.formCreateUserQuery("Sue", "Sue"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// statements to execute
	}

	public void grantPermission(Connection conn) {
		// grants queries

		Statement statement = null;
		try {

			statement = conn.createStatement();
			// grant db designer

			statement.executeUpdate("GRANT CREATE ON TABLE SM.* TO 'Tom'@'localhost'");
			statement.executeUpdate("GRANT ALTER ON TABLE SM.* TO 'Tom'@'localhost'");
			statement.executeUpdate("GRANT DROP ON TABLE SM.* TO 'Tom'@'localhost'");

			statement.executeUpdate("GRANT CREATE ON TABLE SM.* TO 'Joe'@'localhost'");
			statement.executeUpdate("GRANT ALTER ON TABLE SM.* TO 'Joe'@'localhost'");
			statement.executeUpdate("GRANT DROP ON TABLE SM.* TO 'Joe'@'localhost'");

			// grant cruise manager

			List<String> cruiseManagerTableList = new ArrayList<String>();
			cruiseManagerTableList.add("ship");
			cruiseManagerTableList.add("cabin");
			cruiseManagerTableList.add("cruise");

			List<String> cruiseManagerUsersList = new ArrayList<String>();
			cruiseManagerUsersList.add("Kevin");
			cruiseManagerUsersList.add("Victoria");

			List<String> operationList = new ArrayList<String>();
			operationList.add("INSERT");
			operationList.add("DELETE");
			operationList.add("UPDATE");

			this.grantPermission(conn, cruiseManagerTableList, cruiseManagerUsersList, operationList);

			// travel agent

			List<String> travelAgentTableList = new ArrayList<String>();
			travelAgentTableList.add("cabinReservation");
			travelAgentTableList.add("customerReservation");
			travelAgentTableList.add("customer");
			travelAgentTableList.add("phoneNumber");

			List<String> travelAgentUsersList = new ArrayList<String>();
			travelAgentUsersList.add("Robert");
			travelAgentUsersList.add("Amy");

			operationList.add("SELECT");

			this.grantPermission(conn, travelAgentTableList, travelAgentUsersList, operationList);

			// Forming permissions tables for Sue
			List<String> allTables = new ArrayList<String>();
			allTables.addAll(travelAgentTableList);
			allTables.addAll(cruiseManagerTableList);
			allTables.add("reservation");

			List<String> userList = new ArrayList<String>();
			userList.add("Sue");
			List<String> operations = new ArrayList<String>();
			operations.add("SELECT");

			this.grantPermission(conn, allTables, userList, operations);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// satements
	}*/

	// Function to create Ship objects and return list of those objects

	

	// Function to create cruise objects and return list of those objects

	/*private List<Cruise> getCruiseObjects() {
		List<Cruise> cruiseObjectList = new ArrayList<Cruise>();

		Cruise cruiseObject1 = new Cruise();
		cruiseObject1.setCruiseNo(000001);

		cruiseObject1.setStartDate("05/14/2017");
		cruiseObject1.setEndDate("05/19/2017");
		cruiseObject1.setDestination("Iceland");
		cruiseObject1.setShipNO("ABC12384M853");

		Cruise cruiseObject2 = new Cruise();
		cruiseObject2.setCruiseNo(000002);
		cruiseObject2.setStartDate("06/02/2017");
		cruiseObject2.setEndDate("06/10/2017");
		cruiseObject2.setDestination("Jamaica");
		cruiseObject2.setShipNO("PDF3482K9392");

		Cruise cruiseObject3 = new Cruise();
		cruiseObject3.setCruiseNo(000003);
		cruiseObject3.setStartDate("06/12/2017");
		cruiseObject3.setEndDate("05/16/2017");
		cruiseObject3.setDestination("Norway");
		cruiseObject3.setShipNO("ODL83924J383");

		Cruise cruiseObject4 = new Cruise();
		cruiseObject4.setCruiseNo(000004);
		cruiseObject4.setStartDate("07/05/2017");
		cruiseObject4.setEndDate("07/12/2017");
		cruiseObject4.setDestination("Cuba");
		cruiseObject4.setShipNO("MDR98342J834");

		cruiseObjectList.add(cruiseObject1);
		cruiseObjectList.add(cruiseObject2);
		cruiseObjectList.add(cruiseObject3);
		cruiseObjectList.add(cruiseObject4);

		return cruiseObjectList;
	}
*/
	/*private List<Phone> getPhoneNumberObjects() {
		List<Phone> phones = new ArrayList<Phone>();

		Phone phone1 = new Phone();
		phone1.setCustomerNo(10000082L);
		phone1.setPhoneNumber("1234");
		phone1.setType("Mobile");

		Phone phone2 = new Phone();
		phone2.setCustomerNo(10000083L);
		phone2.setPhoneNumber("2345");
		phone2.setType("Mobile");

		Phone phone3 = new Phone();
		phone3.setCustomerNo(10000084L);
		phone3.setPhoneNumber("5678");
		phone3.setType("Mobile");

		phones.add(phone1);
		phones.add(phone2);
		phones.add(phone3);

		return phones;

	}

	// Function to create cabin objects and return list of those objects

	private List<CabinReservation> getCabinReservationObject() {
		List<CabinReservation> cabinReservations = new ArrayList<CabinReservation>();

		CabinReservation cabinReservation = new CabinReservation();

		cabinReservation.setCabinNo(8001);
		cabinReservation.setCabinReservationID(000000000001L);
		cabinReservation.setReservationNo(000000000001L);
		cabinReservation.setShipNo("ABC12384M853");

		CabinReservation cabinReservation2 = new CabinReservation();

		cabinReservation2.setCabinNo(8002);
		cabinReservation2.setCabinReservationID(000000000002L);
		cabinReservation2.setReservationNo(000000000002L);
		cabinReservation2.setShipNo("MDR98342J834");

		CabinReservation cabinReservation3 = new CabinReservation();

		cabinReservation3.setCabinNo(8003);
		cabinReservation3.setCabinReservationID(000000000003L);
		cabinReservation3.setReservationNo(000000000003L);
		cabinReservation3.setShipNo("ODL83924J383");

		cabinReservations.add(cabinReservation);
		cabinReservations.add(cabinReservation2);
		cabinReservations.add(cabinReservation3);

		return cabinReservations;
	}
*/
	/*private List<Cabin> getCabinObjects() {
		List<Cabin> cabinObjectList = new ArrayList<Cabin>();

		Cabin cabinObject1 = new Cabin();
		cabinObject1.setCabinNo(8001);
		cabinObject1.setshipNo("ABC12384M853");
		cabinObject1.setFloor(8);
		cabinObject1.setCabinPricePerNight(225);
		cabinObject1.setNumberOfBeds(1);

		Cabin cabinObject2 = new Cabin();
		cabinObject2.setCabinNo(8002);
		cabinObject2.setshipNo("MDR98342J834");
		cabinObject2.setFloor(8);
		cabinObject2.setCabinPricePerNight(225);
		cabinObject2.setNumberOfBeds(1);

		Cabin cabinObject3 = new Cabin();
		cabinObject3.setCabinNo(8003);
		cabinObject3.setshipNo("ODL83924J383");
		cabinObject3.setFloor(8);
		cabinObject3.setCabinPricePerNight(275);
		cabinObject3.setNumberOfBeds(2);

		Cabin cabinObject4 = new Cabin();
		cabinObject4.setCabinNo(8004);
		cabinObject4.setshipNo("PDF3482K9392");
		cabinObject4.setFloor(8);
		cabinObject4.setCabinPricePerNight(275);
		cabinObject4.setNumberOfBeds(2);

		cabinObjectList.add(cabinObject1);
		cabinObjectList.add(cabinObject2);
		cabinObjectList.add(cabinObject3);
		cabinObjectList.add(cabinObject4);

		return cabinObjectList;

	}
*/
	// Function to create reservation objects and return list of those objects

/*	private List<Reservation> getReservationObjects() {
		List<Reservation> reservationObjectList = new ArrayList<Reservation>();

		Reservation reservationObject1 = new Reservation();
		reservationObject1.setReservationNo(000000000001);
		reservationObject1.setCruiseNo(000001);
		reservationObject1.setDateReservationMade("02/21/2017");
		reservationObject1.setDatePaid("02/21/2017");

		Reservation reservationObject2 = new Reservation();
		reservationObject2.setReservationNo(000000000002);
		reservationObject2.setCruiseNo(000002);
		reservationObject2.setDateReservationMade("04/25/2017");
		reservationObject2.setDatePaid("04/25/2017");

		Reservation reservationObject3 = new Reservation();
		reservationObject3.setReservationNo(000000000003);
		reservationObject3.setCruiseNo(000003);
		reservationObject3.setDateReservationMade("03/16/2017");
		reservationObject3.setDatePaid("03/16/2017");

		Reservation reservationObject4 = new Reservation();
		reservationObject4.setReservationNo(000000000004);
		reservationObject4.setCruiseNo(000004);
		reservationObject4.setDateReservationMade("04/21/2017");
		reservationObject4.setDatePaid("04/21/2017");

		reservationObjectList.add(reservationObject1);
		reservationObjectList.add(reservationObject2);
		reservationObjectList.add(reservationObject3);
		reservationObjectList.add(reservationObject4);

		return reservationObjectList;
	}
*/
	/*private List<CustomerReservation> getCustomerReservationObjects() {

		List<CustomerReservation> customerReservations = new ArrayList<CustomerReservation>();

		CustomerReservation customerReservation = new CustomerReservation();
		customerReservation.setCustomerNo(10000082L);
		customerReservation.setReservationNo(000000000001);

		CustomerReservation customerReservation2 = new CustomerReservation();
		customerReservation2.setCustomerNo(10000083L);
		customerReservation2.setReservationNo(000000000002);

		CustomerReservation customerReservation3 = new CustomerReservation();
		customerReservation3.setCustomerNo(10000084L);
		customerReservation3.setReservationNo(000000000003);

		customerReservations.add(customerReservation);
		customerReservations.add(customerReservation2);
		customerReservations.add(customerReservation3);

		return customerReservations;

	}*/

	// Function to create customer objects and return list of those objects
	private List<Customer> getCustomerObjects() {
		List<Customer> userList = new ArrayList<Customer>();
		Customer usersObject1 = new Customer();
		
		usersObject1.setFirstName("Rob");
		usersObject1.setLastName("McDonald");
		usersObject1.setuserID(1);
		usersObject1.setEmail("rob.mcd@gmail.com");
		
		
		Customer userObject2 = new Customer();
		userObject2.setFirstName("Pooja");
		userObject2.setLastName("abc");
		userObject2.setuserID(2);
		userObject2.setEmail("pooja@gmail.com");

		Customer userObject3 = new Customer();
		userObject3.setFirstName("asdf");
		userObject3.setLastName("qwer");
		userObject3.setuserID(3);
		userObject3.setEmail("poojadddse@gmail.com");

		userList.add(usersObject1);
		userList.add(userObject2);
		userList.add(userObject3);

		return userList;

	}
	
	private List<Ship> getPostObjects() {
		List<Ship> postObjectList = new ArrayList<Ship>();

		Ship postObject1 = new Ship();
		postObject1.setPostID(1);
		postObject1.setContent("test msg");
		postObject1.setdatePosted("1/1/2000");
		postObject1.setUserID(1);
		
		Ship postObject2 = new Ship();
		postObject2.setPostID(2);
		postObject2.setContent("test2 msg");
		postObject2.setdatePosted("1/1/2000");
		postObject2.setUserID(1);

		Ship postObject3 = new Ship();
		postObject3.setPostID(2);
		postObject3.setContent("test2 msg");
		postObject3.setdatePosted("1/1/2000");
		postObject3.setUserID(1);

		Ship postObject4 = new Ship();
		postObject4.setPostID(2);
		postObject4.setContent("test2 msg");
		postObject4.setdatePosted("1/1/2000");
		postObject4.setUserID(1);

		Ship postObject5 = new Ship();
		postObject5.setPostID(2);
		postObject5.setContent("test2 msg");
		postObject5.setdatePosted("1/1/2000");
		postObject5.setUserID(2);;

		return postObjectList;
	}

	// Function to create Phone objects and return list of those objects

	// Function to create Phone objects and return list of those objects

	public void insertValuesToTables(Connection conn) {
		// insert queries

		// insert into SHIP
		// create ship objects
		this.insertIntoShipTables(conn);

		// create a method to insert to customer table ( // create a method to
		// insert into phonenumber)
		this.insertIntoCustomerTable(conn);

		//this.insertIntoCruiseTable(conn);
		//this.insertIntoReservationTable(conn);
		// because phonnumebr table contains custoemr id
		// that means firts insert into customer table table then insert into
		// phone numberd

		//this.insertIntoCabinTable(conn);

		//this.insertIntoPhoneNumberTable(conn);

		// create amethod toninsert into cabin

		//this.insertIntoCustomerReservationTable(conn);

		//this.insertIntoCabinReservationTable(conn);

		// create objects

		// statements
	}
/*
	private void insertIntoCabinReservationTable(Connection conn) {

		PreparedStatement pst = null;
		String insertIntoCabinReservationQuery1 = "INSERT INTO cabinReservation VALUES "
				+ "(000000000001, 8001, 'ABC12384M854', 000000000001);";

		String insertIntoCabinReservationQuery = "INSERT INTO cabinReservation VALUES " + "(?, ?, ?, ?);";

		try {

			for (CabinReservation cabinReservation : this.getCabinReservationObject()) {
				pst = conn.prepareStatement(insertIntoCabinReservationQuery);
				pst.setLong(1, cabinReservation.getCabinReservationID());
				pst.setInt(2, cabinReservation.getCabinNo());
				pst.setString(3, cabinReservation.getShipNo());
				pst.setLong(4, cabinReservation.getReservationNo());

				pst.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertIntoCustomerReservationTable(Connection conn) {

		String insertIntoCustomerReservationQuery1 = "INSERT INTO customerReservation VALUES "
				+ "(000000000001, 000000000001);";
		String insertIntoCustomerReservationQuery = "INSERT INTO customerReservation VALUES " + "(?, ?);";
		PreparedStatement pst = null;

		try {
			for (CustomerReservation customerReservation : this.getCustomerReservationObjects()) {
				pst = conn.prepareStatement(insertIntoCustomerReservationQuery);
				pst.setLong(1, customerReservation.getCustomerNo());
				pst.setInt(2, customerReservation.getReservationNo());

				pst.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertIntoPhoneNumberTable(Connection conn) {

		String insertIntoPhoneNumberQuery = "INSERT INTO phoneNumber VALUES " + "(?,?, ?);";
		PreparedStatement pst = null;

		try {

			for (Phone phone : this.getPhoneNumberObjects()) {
				pst = conn.prepareStatement(insertIntoPhoneNumberQuery);
				pst.setString(1, phone.getPhoneNumber());
				pst.setLong(2, phone.getCustomerNo());
				pst.setString(3, phone.getType());

				pst.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertIntoCabinTable(Connection conn) {

		String insertIntoCabinQuery1 = "INSERT INTO cabin VALUES (8001, 'ABC12384M854', 8, 225, 1);";
		String insertIntoCabinQuery = "INSERT INTO cabin VALUES (?, ?, ?, ?, ?);";
		PreparedStatement pst = null;
		try {
			for (Cabin cabin : this.getCabinObjects()) {
				pst = conn.prepareStatement(insertIntoCabinQuery);
				pst.setInt(1, cabin.getCabinNo());
				pst.setString(2, cabin.getShipNo());
				pst.setInt(3, cabin.getFloor());
				pst.setFloat(4, cabin.getCabinPricePerNight());
				pst.setInt(5, cabin.getNumberOfBeds());

				pst.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertIntoReservationTable(Connection conn) {

		// String insertIntoReservationQuery1 =
		// "INSERT INTO reservation VALUES (000000000001, 000001, "
		// +
		// "STR_TO_DATE('02/21/2017','%m/%d/%Y'),
		// STR_TO_DATE('02/21/2017','%m/%d/%Y'));";

		String insertIntoReservationQuery = "INSERT INTO reservation VALUES (?, ?, "
				+ "STR_TO_DATE(?,'%m/%d/%Y'), STR_TO_DATE(?,'%m/%d/%Y'));";

		PreparedStatement pst = null;

		try {

			for (Reservation reservation : this.getReservationObjects()) {
				pst = conn.prepareStatement(insertIntoReservationQuery);

				pst.setInt(1, reservation.getReservationNo());
				pst.setInt(2, reservation.getCruiseNo());
				pst.setString(3, reservation.getDateReservationMade());
				pst.setString(4, reservation.getDatePaid());

				pst.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
*/
	private void insertIntoShipTables(Connection conn) {
		String insertIntoShipQuery = "INSERT INTO ship (postID,Content,datePosted,userID) "
				+ "VALUES (?,?,?, ?);";

		for (Ship shipObject : this.getPostObjects()) {

			try {
				PreparedStatement pst = conn.prepareStatement(insertIntoShipQuery);

				pst.setInt(1, shipObject.getPostID());
				pst.setString(2, shipObject.getContent());
				pst.setString(3, shipObject.getdatePosted());

				pst.setInt(4, shipObject.getUserID());
			
				pst.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void insertIntoCustomerTable(Connection conn) {
		
		String insertIntoCustomerTableQuery = "INSERT INTO user VALUES " + "(?, ?, ?,?);";

		

		System.out.println("Size : " + this.getCustomerObjects());
		PreparedStatement pst = null;
		for (Customer customerObeject : this.getCustomerObjects()) {

			try {
				pst = conn.prepareStatement(insertIntoCustomerTableQuery);

				pst.setInt(1, customerObeject.getUserID());
				pst.setString(2, customerObeject.getFirstName());
				pst.setString(3, customerObeject.getLastName());
				pst.setString(4, customerObeject.getEmail());
				pst.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
/*
	private void insertIntoCruiseTable(Connection conn) {

		// String insertIntoCruiseQuery1 = "INSERT INTO cruise VALUES "
		// +
		// "(000001,STR_TO_DATE('05/14/2017','%m/%d/%Y'),STR_TO_DATE('05/19/2017','%m/%d/%Y'),
		// "
		// + "'Iceland', 'ABC12384M854');";

		String insertIntoCruiseQuery = "INSERT INTO cruise VALUES "
				+ "(?,STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'),?, ?);";

		PreparedStatement pst = null;

		try {
			for (Cruise cruise : this.getCruiseObjects()) {

				pst = conn.prepareStatement(insertIntoCruiseQuery);
				pst.setInt(1, cruise.getCruiseNo());
				pst.setString(2, cruise.getStartDate());
				pst.setString(3, cruise.getEndDate());
				pst.setString(4, cruise.getDestination());
				pst.setString(5, cruise.getShipNO());

				pst.execute();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	String formGrantPermissionToDbDesigneQuery(String userName) {
		return "GRANT CREATE ON TABLE OC.* TO '" + userName + "'@'localhost';" + "GRANT ALTER ON TABLE OC.* TO '"
				+ userName + "'@'localhost;" + "GRANT DROP ON TABLE OC.* TO '" + userName + "'@'localhost;";
	}

	String formCreateUserQuery(String userName, String password) {
		return "CREATE USER '" + userName + "'@'localhost' IDENTIFIED BY '" + password + "'";
	}

	String formGrantInsertPermissionQuery(String tableName, String user) {
		return "GRANT INSERT ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	String formGrantUpdatePermissionQuery(String tableName, String user) {
		return "GRANT UPDATE ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	String formGrantDeletePermissionQuery(String tableName, String user) {
		return "GRANT DELETE ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	String formGrantSelectPermissionQuery(String tableName, String user) {
		return "GRANT SELECT ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	void grantPermission(Connection conn, List<String> tables, List<String> users, List<String> operations) {

		for (String user : users) {
			for (String table : tables) {
				for (String operation : operations)
					try {
						System.out.println("user" + user + " table :" + table + " operation :" + operation);
						Statement statement = conn.createStatement();
						statement.executeUpdate(
								"GRANT " + operation + " ON " + table + " TO '" + user + "'@'localhost'");
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
*/
}
