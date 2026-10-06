import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class familialDatabase
{
private static int counterOne = 0; // Counter to ensure that the table is only populated once with the user's details.
private static int counterTwo = 0; // Counter to ensure that the table is only populated once with the user's family details.
static HashMap<String,String> users = new HashMap<>(); // Declares a haspmap to store users details
final private static String[] columnNames = {"userID", "username", "password", "firstName", "lastName", "age", "country", "phoneNumber"};

    public static void main(String[] args) 
    {
       String query = "SELECT username,password FROM familyDetails"; //Establishes a query to get sets of usernames and passwords and loads them into the hashmap in order to have a list to compare to when the user enters in a username and password.
        var url2 = "jdbc:sqlite:C:/Users/liamm/familyDetail.db"; // Connection url to the database.
        try(Connection conn = DriverManager.getConnection(url2)) // Attempts to establish a connection to the database.
        {
        PreparedStatement pps = conn.prepareStatement(query); // Creates a prepared statement that defends against SQL injection and is pre-compiled.
        ResultSet rs = pps.executeQuery(); //Creates a result set.

        while(rs.next()) // Iterates through the data in the result set and adds
        {
          String username = rs.getString("username");
          String password = rs.getString("password");
          users.put(username,password); // Puts the user's username and password in the hashmap to be compared with later.
        }
        }
        catch(Exception g)
        {
        System.out.println(g.getMessage()); // Error message.
        }

      JFrame frame = Extras.frameMethod(); // Creates a frame to hold all of the elements.

       CardLayout CardLayout = new CardLayout(); // Creates a card layout to allow for page traversal.
       JPanel cardlayout = new JPanel(CardLayout);
       cardlayout.setOpaque(false);

       frame.getContentPane().add(cardlayout,BorderLayout.CENTER); // Adds card layout to the frame.

       JPanel login = new JPanel(new GridBagLayout()); // Frame that allows the middle of the screen to be filled with things.
       login.setOpaque(false);

       GridBagConstraints gbc = Extras.gbc(); // Adds constraints.
       
       JPanel center = Extras.centre();
       login.add(center,gbc); // Applies the constraints and the centre frame to the login card frame.

       JTextField usernameTextbox = new JTextField("Please enter in your username",20); 
       center.add(usernameTextbox);

       JPanel strip = new JPanel(); 
       strip.setBackground(Color.gray);
       center.add(strip);

       JPasswordField passwordPasswordField = new JPasswordField(20); 
       center.add(passwordPasswordField);

       JButton submitButton = new JButton("Submit details"); 
       submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       center.add(submitButton);

       JButton create = new JButton("Add another member of the family"); 
       create.setAlignmentX(Component.CENTER_ALIGNMENT);
       center.add(create);

       submitButton.addActionListener( (e) -> // Allows the button to take the user to the main menu if they have a correct username and password. It also alerts the user if they have forgotten to delete the default text.
       { 
        if(usernameTextbox.getText().equals("Please enter in your username")) 
        {
          JOptionPane.showMessageDialog(null,"Please delete the text and input a username");
        }
        
        if(users.containsKey(usernameTextbox.getText()) && users.get(usernameTextbox.getText()).equals(new String(passwordPasswordField.getPassword())))
        {
        CardLayout.show(cardlayout,"menu"); 
        }
        else
        {
        JOptionPane.showMessageDialog(null,"Please re-enter a valid password");
        }
        
       });

       create.addActionListener( (e) -> // Allows the user to be taken to the family member addition menu.
       {
       CardLayout.show(cardlayout,"createMenu"); 
       });
    
       JPanel menu = new JPanel(new GridBagLayout()); 
       menu.setOpaque(false);
       
       GridBagConstraints gbn = Extras.gbc();

       JPanel center2 = Extras.centre();
       menu.add(center2,gbn);

       JButton viewPersonalDetailsButton = new JButton("View personal details"); 
       viewPersonalDetailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       center2.add(viewPersonalDetailsButton);

       JPanel createMenu = new JPanel(new GridBagLayout()); 
       createMenu.setOpaque(false);

       JPanel centerUser = new JPanel(); 
       centerUser.setBackground(Color.gray);
       centerUser.setLayout(new BoxLayout(centerUser, BoxLayout.Y_AXIS));
       createMenu.add(centerUser,gbn);

       JTextField usernameCreate = new JTextField("Please enter in your username",20); 
       centerUser.add(usernameCreate);

       JPasswordField passwordCreate = new JPasswordField("Please enter in your password",20); 
       centerUser.add(passwordCreate);

       JTextField firstNameCreate = new JTextField("Please enter in your first name",20); 
       centerUser.add(firstNameCreate);

       JTextField lastNameCreate = new JTextField("Please enter in your last name",20); 
       centerUser.add(lastNameCreate);

       JTextField ageCreate = new JTextField("Please enter in your age",20); 
       centerUser.add(ageCreate);

       JTextField countryCreate = new JTextField("Please enter in your country",20); 
       centerUser.add(countryCreate);

       JTextField phoneNumberCreate = new JTextField("Please enter in your phone number",20); 
       centerUser.add(phoneNumberCreate);

       JButton createAcc = new JButton("Create member");
       createAcc.setAlignmentX(Component.CENTER_ALIGNMENT);
       centerUser.add(createAcc);

       JButton returnToLogin = new JButton("Go back to login"); 
       returnToLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
       centerUser.add(returnToLogin);

       createAcc.addActionListener((e) -> // Allows the user to add the details that the user input into the database.
       {
         String queryCreate = "INSERT INTO familyDetails(username,password,firstName,lastName,age,country,phoneNumber) VALUES(?,?,?,?,?,?,?)"; // SQL query to insert values into database.
        var urlCreate = "jdbc:sqlite:C:/Users/liamm/familyDetail.db";
        try(Connection conn = DriverManager.getConnection(urlCreate))
        {
        PreparedStatement pps = conn.prepareStatement(queryCreate); // Takes the values from the textfields and password fields and puts them into the database.

        pps.setString(1,usernameCreate.getText());
        pps.setString(2,new String(passwordCreate.getPassword()));
        pps.setString(3,firstNameCreate.getText());
        pps.setString(4,lastNameCreate.getText());
        pps.setString(5,ageCreate.getText());
        pps.setString(6,countryCreate.getText());
        pps.setString(7,phoneNumberCreate.getText());
        
        int rowsInserted = pps.executeUpdate();

        if (rowsInserted > 0) // A check to see whether or not the family member addition was successful.
        {
          users.put(usernameCreate.getText(), new String(passwordCreate.getPassword()));
          System.out.println("It worked!");
        }
        else
        {
          System.out.println("It failed");
        }
        }
        catch(Exception g)
        {
        System.out.println(g.getMessage());
        }
       });

       returnToLogin.addActionListener((e) -> // Allows the user to the login screen.
      {
      CardLayout.show(cardlayout,"login");
      }
      );
       DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Creates the model for the table.
       JTable personal = new JTable(); // Creates the table to allow the user to hold their personal details.
       personal.setModel(model);
       personal.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
       personal.revalidate();
      
       viewPersonalDetailsButton.addActionListener((e) -> // Allows the user to see their own details.
       {
       CardLayout.show(cardlayout,"personalDetails");
        var url = "jdbc:sqlite:C:/Users/liamm/familyDetail.db";
       try (var conn = DriverManager.getConnection(url))
       {
         System.out.println("Connection has been established");

         String query2 = "SELECT * FROM familyDetails WHERE username = ?"; // SQL query to get user from database.
         try(PreparedStatement s =  conn.prepareStatement(query2))
        {
         s.setString(1,usernameTextbox.getText());
         
        try(ResultSet rs = s.executeQuery())
        {
        if(counterOne < 1) // A check to ensure that the table is not populated with the same data every time the user wants to see the table.
        {
        while (rs.next()) // Iterates through each field and puts them into the table.
         {
         int userID = rs.getInt("userID");
         String username = rs.getString("username");
         String password = rs.getString("password");
         String firstName = rs.getString("firstName");
         String lastName = rs.getString("lastName");
         int age = rs.getInt("age");
         String country = rs.getString("country");
         String phoneNumber = rs.getString("phoneNumber");

         model.addRow(new Object[]{userID, username, password, firstName, lastName, age, country, phoneNumber}); // Adds each attribute value to the table.
         }
       }
      }
      counterOne++;
       }
       catch (SQLException r)
       {
       System.out.println(r.getMessage());
       }
       }
       catch (SQLException r)
       {
        System.out.println(r.getMessage());
       }
       }
       );

       JButton viewFamilyDetailsButton = new JButton("View family ties"); 
       viewFamilyDetailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       center2.add(viewFamilyDetailsButton);

       DefaultTableModel model2 = new DefaultTableModel(columnNames, 0); // Creates the model for the table.
       JTable personal2 = new JTable(); // Creates the table to allow the user to hold their family's details.
       personal2.setModel(model2);
       personal2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

       viewFamilyDetailsButton.addActionListener((e) -> // Allows the user to see the details of their family.
       {
       CardLayout.show(cardlayout,"familyDetails");
       
       var url = "jdbc:sqlite:C:/Users/liamm/familyDetail.db";
       try (var conn = DriverManager.getConnection(url))
       {
         System.out.println("Connection has been established");
         String query3 = "SELECT * FROM familyDetails";
         try(PreparedStatement i =  conn.prepareStatement(query3))
        {
        try(ResultSet is = i.executeQuery())
        {
          if(counterTwo < 1) // A check to ensure that the table is not populated with the same data every time the user wants to see the table.
          {
        while (is.next()) // Iterates through each field and puts them into the table.
         {
         int userID = is.getInt("userID");
         String username = is.getString("username");
         String password = is.getString("password");
         String firstName = is.getString("firstName");
         String lastName = is.getString("lastName");
         int age = is.getInt("age");
         String country = is.getString("country");
         String phoneNumber = is.getString("phoneNumber");
  
         model2.addRow(new Object[]{userID, username, password, firstName, lastName, age, country, phoneNumber}); // Adds each attribute value to the table.
         }
        }
         counterTwo++;
         }
       }
       
       catch (SQLException r)
       {
       System.out.println(r.getMessage());
       }
       }
       catch (SQLException r)
       {
        System.out.println(r.getMessage());
       }
      }
       );
       
       JButton loginButton1= new JButton("Go back to login"); 
       loginButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
       center2.add(loginButton1);

       loginButton1.addActionListener((e) -> // Allows the user to go back to the login screen.
       {
        CardLayout.show(cardlayout,"login");
       });

       JPanel personalDetails = new JPanel(new GridBagLayout());
       personalDetails.setOpaque(false);

       JPanel centre3 = Extras.centre();
       personalDetails.add(centre3,gbc);

       centre3.add(personal);

       JButton menuButton1 = new JButton("Go back to menu"); 
       menuButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
       centre3.add(menuButton1);

       menuButton1.addActionListener((e) -> // Allows the user to return to the main menu.
       {
        CardLayout.show(cardlayout,"menu");
       });

       JPanel familyDetails = new JPanel(new GridBagLayout());
       familyDetails.setOpaque(false);

       JPanel centre4 = Extras.centre();
       familyDetails.add(centre4,gbc);

       
       centre4.add(personal2);

       JButton menuButton2 = new JButton("Go back to menu"); 
       menuButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
       centre4.add(menuButton2);

       menuButton2.addActionListener((e) ->  // Allows the user to return to the main menu.
       {
        CardLayout.show(cardlayout,"menu");
       });

       cardlayout.add(login,"login");
       cardlayout.add(createMenu,"createMenu");
       cardlayout.add(menu,"menu");
       cardlayout.add(personalDetails,"personalDetails");
       cardlayout.add(familyDetails,"familyDetails");
       frame.setVisible(true);
    }
}


