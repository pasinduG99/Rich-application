package com;


import java.sql.*;

public class BillGeneration {
	private Connection connect()
	{
	Connection con = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billdb",
			"root", "Casio123");
	}
	catch (Exception e)
	{e.printStackTrace();}
	return con;
	}
	public String insertBill(String name, String accno, String address, String units, String amount )
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for inserting."; }
	
	String query = " insert into bills(`billno`,`username`,`ano`,`address`,`units`,`amount`)" 
	+ " values (?, ?, ?, ?, ?,?)";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, 0);
	preparedStmt.setString(2, name);
	preparedStmt.setString(3, accno);
	preparedStmt.setString(4, address);
	preparedStmt.setInt(5, Integer.parseInt (units));
	preparedStmt.setDouble(6, Double.parseDouble(amount));
	
	
	// execute the statement
	
	preparedStmt.execute();
	con.close();
	
	String newBills = readBills();
	output = "{\"status\":\"success\", \"data\": \"" + newBills + "\"}";
	
	
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\":\"Error while generating bill.\"}";
		System.err.println(e.getMessage());
	}
	return output;
	}
	public String readBills()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
	// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Name</th><th>AccountNO</th>" +
			"<th>Address</th>" +
			"<th>Units Consumed</th>" +
			"<th>Amount</th>" +
			"<th>Update</th><th>Remove</th></tr>";
	String query = "select * from bills";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	while (rs.next())
	{
		String billno = Integer.toString(rs.getInt("billno"));
		String username = rs.getString("username");
		String ano= rs.getString("ano");
		String address = rs.getString("address");
		String units = Integer.toString(rs.getInt("units"));
		String amount = Double.toString(rs.getDouble("amount"));
		
		// Add into the html table
		output += "<tr><td><input id='hidBillNOUpdate'name='hidBillNOUpdate' type='hidden' value='" + billno + "'>"
				+ username + "</td>";
		output += "<td>" + ano + "</td>";
		output += "<td>" + address + "</td>";
		output += "<td>" + units + "</td>";
		output += "<td>" + amount + "</td>";
		
		// buttons
		output += "<td><input name='btnUpdate' type='button' value='Update' "
				+ "class='btnUpdate btn btn-secondary' data-billno='" + billno + "'></td>"
				+ "<td><input name='btnRemove' type='button' value='Remove' "
				+ "class='btnRemove btn btn-danger' data-billno='" + billno + "'></td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
		output = "Error while reading the bills.";
		System.err.println(e.getMessage());
	}
	return output;
	}
	public String updateBill(String billno , String name , String ano, String address, String units, String amount)
	
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
		{
			return "Error while connecting to the database for updating."; 
		}
		// create a prepared statement
		String query = "UPDATE bills SET username=?,ano=?,address=?,units=?,amount=? WHERE billno=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, ano);
		preparedStmt.setString(3, address);
		preparedStmt.setInt(4, Integer.parseInt(units)) ;
		preparedStmt.setDouble(5, Double.parseDouble(amount));
		preparedStmt.setInt(6, Integer.parseInt(billno));
		// execute the statement
		preparedStmt.execute();
		con.close();
		
		String newBills = readBills();
		output = "{\"status\":\"success\", \"data\": \"" + newBills + "\"}";
		
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
		}
		public String deleteBill(String billno)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{
			return "Error while connecting to the database for deleting."; 
		}
		// create a prepared statement
		String query = "delete from bills where billno=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		preparedStmt.setInt(1, Integer.parseInt(billno));
		// execute the statement
		preparedStmt.execute();
		con.close();
		String newBills = readBills();
		output = "{\"status\":\"success\", \"data\": \"" + newBills + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
		}

}
