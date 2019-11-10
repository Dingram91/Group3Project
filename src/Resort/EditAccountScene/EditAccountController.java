package Resort.EditAccountScene;

import Resort.ManagerViewScene.ManagerViewController;
import Resort.TitleScene.TitleController;
import Resort.Utility.AccountInformation;
import Resort.Utility.DatabaseAgent;
import Resort.Utility.SessionInformation;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditAccountController {

  @FXML
  private AnchorPane btnCreateAccount;

  @FXML
  private TextField tfFirstName;

  @FXML
  private TextField tfLastName;

  @FXML
  private TextField tfUsername;

  @FXML
  private TextField tfEmail;

  @FXML
  private TextField tfPassword;

  @FXML
  private TextField tfConfirmPassword;

  @FXML
  private TextField tfAddress;

  @FXML
  private TextField tfState;

  @FXML
  private TextField tfzipcode;

  @FXML
  private TextField tfCreditCardNumber;

  @FXML
  private TextField tfCvv;

  @FXML
  private Label lblCreateIndicate;

  @FXML
  private Button btnReturnToManager;

  // String to hold our username if logged in and if user is a manager
  private SessionInformation sessionInformation = new SessionInformation();

  private String userNameForManagerEdit;

  public void initialize() {
    // For users editing their own account managerView button will be disabled
    btnReturnToManager.setVisible(false);
  }
  // Setter so that when called from manager view to edit another users account this can be set
  public void setUserNameForManagerEdit(String userNameForManagerEdit) {
    this.userNameForManagerEdit = userNameForManagerEdit;

    btnReturnToManager.setVisible(true);

    AccountInformation userAccount = DatabaseAgent.getAccountInformation(userNameForManagerEdit);

    tfFirstName.setText(userAccount.getFirstName());
    tfLastName.setText(userAccount.getLastName());
    tfUsername.setText(userAccount.getUserName());
    tfEmail.setText(userAccount.getEmail());
    tfAddress.setText(userAccount.getAddress());
    tfState.setText(userAccount.getState());
    tfzipcode.setText(userAccount.getZipCode());
    tfPassword.setText(userAccount.getPassWord());
    tfConfirmPassword.setText(userAccount.getPassWord());
    tfCvv.setText(userAccount.getCvv());

  }

  // setter for session information
  public void setSessionInformation(SessionInformation sessionInformation) {
    this.sessionInformation = sessionInformation;
  }

  // setter for session information
  public void setSessionInformation(SessionInformation sessionInformation, String targetUser) {
    this.sessionInformation = sessionInformation;
    userNameForManagerEdit = targetUser;
  }

  @FXML
  void btnClickUpdateAccount(MouseEvent event) {
    //todo add logic to update the account in the database
  }

  @FXML
  void btnClickHome(MouseEvent event) throws IOException {
    //get a reference to the window we are in
    Stage window = (Stage) btnCreateAccount.getScene().getWindow();

    // declare and initialize a loader for the FXML scene we are going to
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("../TitleScene/Title.fxml"));

    // create a parent class with our loader pointing at the new scene
    Parent title = loader.load();

    // get controller for Title page
    TitleController titleController = loader.getController();
    titleController.setSessionInformation(sessionInformation);
    // make the new scene we are going to
    Scene titleScene = new Scene(title);

    // initiate the scene change (no need to make changes to controller)
    window.setScene(titleScene);
  }

  public void setSessioninformation(SessionInformation sessionformation) {
    this.sessionInformation = sessionformation;

    // If this is called from the manager view to edit another users information set info for that user
    AccountInformation userAccount = DatabaseAgent.getAccountInformation(sessionformation.getUserName());

    System.out.println("Test");

    tfFirstName.setText(userAccount.getFirstName());
    tfLastName.setText(userAccount.getLastName());
    tfUsername.setText(userAccount.getUserName());
    tfEmail.setText(userAccount.getEmail());
    tfAddress.setText(userAccount.getAddress());
    tfState.setText(userAccount.getState());
    tfzipcode.setText(userAccount.getZipCode());
    tfPassword.setText(userAccount.getPassWord());
    tfConfirmPassword.setText(userAccount.getPassWord());
    tfCvv.setText(userAccount.getCvv());
  }

  @FXML
  void clickReturnToManager(ActionEvent event) throws IOException {
    Stage window = (Stage)  tfUsername.getScene().getWindow();

    // declare and initialize a loader for the FXML scene we are going to
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("../ManagerViewScene/ManagerView.fxml"));

    // create a parent class with our loader pointing at the new scene
    Parent managerView = loader.load();

    // get controller for Title page
    ManagerViewController managerViewController = loader.getController();

    managerViewController.setSessionInformation(sessionInformation);


    // make the new scene we are going to
    Scene managerScene = new Scene(managerView);

    // initiate the scene change (no need to make changes to controller)
    window.setScene(managerScene);
  }

  @FXML
  void clickUpdateAccount(ActionEvent event) throws SQLException {
    int userId = DatabaseAgent.getAccountInformation(sessionInformation.getUserName()).getUserId();
    // if a manager is editing someones account username needs to be reassigned
    if (userNameForManagerEdit != null) {
      userId = DatabaseAgent.getAccountInformation(userNameForManagerEdit).getUserId();
    }
    AccountInformation accountInformation = new AccountInformation();
    accountInformation.setFirstName(tfFirstName.getText());
    accountInformation.setLastName(tfLastName.getText());
    accountInformation.setPassWord(tfPassword.getText());
    accountInformation.setUserName(tfUsername.getText());
    accountInformation.setEmail(tfEmail.getText());
    accountInformation.setAddress(tfAddress.getText());
    accountInformation.setState(tfState.getText());
    accountInformation.setZipCode(tfzipcode.getText());
    accountInformation.setCreditCardNumber(tfCreditCardNumber.getText());
    accountInformation.setCvv(tfCvv.getText());
    accountInformation.setUserId(userId);

    DatabaseAgent.updateAccount(accountInformation);
  }

}
