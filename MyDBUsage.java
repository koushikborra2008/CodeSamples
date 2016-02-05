
/*
 *
 * This program was originally adapted from one of the samples supplied
 * with Oracle; however, it has been substantially rewritten.
 *
 * Author: Koushik Borra
 *Program Name: To extract data from database based using JDBC basing on user input
 */
// You need to import the java.sql package to use JDBC
import java.sql.*;
import java.io.*;

class MyDBUsage {

    static BufferedReader keyboard;  // Needed for keyboard I/O.
    static Connection conn; // A connection to the DB must be established
    // before requests can be handled.  You should
    // should have only one connection.
    static Statement stmt;  // Requests are sent via Statements.  You need
    // one statement for everty request you have
    // open at the same time.

    // "main" is where the connection to the database is made, and
    // where I/O is presented to allow the user to direct requests to
    // the methods that actually do the work.
    public static void main(String args[])
            throws IOException {
        String username = "c##kb140";
        String password = "ny76aFVr";
        String ename;
        int original_empno = 0;
        int empno;
        String querySelection = "nullValue";
        String bName;
        ResultSet rsetInner = null;
        ResultSet rset = null;
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        try { //Errors will throw a "SQLException" (caught below)


            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            System.out.println("Registered the driver...");

            // Connect to the database.  The first argument is the
            // connection string, the second is your username, the third is
            // your password.
            conn = DriverManager.getConnection("jdbc:oracle:thin:@toolman.wiu.edu:1521:orcl", username, password);

            System.out.println("logged into oracle as " + username);

            // Create a Statement; again, you may have/need more than one.
            stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            //while loop to display user choice of menu until he types a 5 in the menu
            while (!querySelection.equals("5")) {
                System.out.println("Select the query you want ? 1 for Number of books read," + "\n" + "2 to find the number of times each book was read," + "\n" + "3 t$
                querySelection = keyboard.readLine();
                // System.out.println(querySelection);
                if (querySelection.equals("1")) {
                    rset = stmt.executeQuery("SELECT COUNT(Bid) FROM Read");

                    while (rset.next()) {
                        System.out.println("The total number or books read is " + rset.getInt(1));
                    }
                    System.out.println("\n");
                }//End if for for selection equal to 1
                if (querySelection.equals("2")) {
                    rset = stmt.executeQuery("SELECT Bid, COUNT(Rid) FROM Read GROUP BY Bid");
                    System.out.println("Bid\t\t#TimesRead");
                    while (rset.next()) {
                        //System.out.println("Bid\t\t#TimeRead");
                        System.out.println(rset.getInt(1) + "\t\t" + rset.getInt(2));
                    }
                    System.out.println("\n");
                }//End if for for selection equal to 2
                if (querySelection.equals("3")) {
                    System.out.println("Select the book name that you want to check with ?");

                    bName = keyboard.readLine();
                    rset = stmt.executeQuery("SELECT Read.Rid,Read.RDate FROM Books INNER JOIN Read ON Books.Bid = Read.Bid AND Books.BName ='" + bName + "'");
                    ResultSetMetaData rMeta = rset.getMetaData();
                    System.out.println(rMeta.getColumnName(1) + "\t\t" + rMeta.getColumnName(2));
                    while (rset.next()) {
                        System.out.println(rset.getInt(1) + "\t" + rset.getDate(2));
                    }
                    System.out.println("\n");
                }//End if for for selection equal to 3
                if (querySelection.equals("4")) {
                    rset = stmt.executeQuery("SELECT DISTINCT Bid,BName FROM Books");
                    while (rset.next()) {
                        System.out.println("\n\n\n\t\t\t\t" + rset.getString(2) + "\n\n\n");
                        rsetInner = stmt2.executeQuery("SELECT RevDate,Ratings,Comments FROM Review WHERE Bid =" + rset.getInt(1));
                        ResultSetMetaData rMeta = rsetInner.getMetaData();
                        System.out.println(rMeta.getColumnName(1) + "\t\t" + rMeta.getColumnName(2) + "\t\t" + rMeta.getColumnName(3));
                        while (rsetInner.next()) {
                            System.out.println(rsetInner.getDate(1) + "\t" + rsetInner.getInt(2) + "\t" + rsetInner.getString(3));
                        }
                    }
         }//End if for for selection equal to 4
                if (querySelection.equals("5")) {
                    System.out.println("Exiting the loop, Bye !!!" + "\n");
                    conn.close();
                }//End if for for selection equal to 5
            }//End while

        } // ends the try
        catch (SQLException e) {
            System.out.println("Caught SQL Exception: \n     " + e);
        }
    }
}

