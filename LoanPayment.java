/** Simple JavaFX Loan Calculator
  *	This is a simple Monthly loan payment calculator using the JavaFX technology of the Java language.
  * The user interface takes the principal amount, number of years and interest rate from the user and calculates and displays the monthly loan amount.
  * It also displays the total amount of loan payment payable.
  * The program makes sure the user enters valid numbers, and also informs the user if the input data is not as expected by the program.

**/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.*;
import java.text.NumberFormat;

public class LoanPayment extends Application{

	private TextField principalTextField;
	private TextField yearTextField;
	private TextField interestTextField;
	private TextField monthlyPaymentTextField;
	private TextField totalAmountPayableTextField;
	private Button calculateButton;
	private Label errorLabel;

	public static void main(String[] args){

		Application.launch(args);


	}
	public void start(Stage primaryStage){
		GridPane pane = new GridPane();
		Text instruction = new Text("Calculate Monthly Loan Payments");
		instruction.setFont(new Font(20));
		HBox topBox = new HBox(instruction);
		topBox.setPadding(new Insets(20, 10, 10, 20));

		Label principalLabel = new Label("Principal");
		principalLabel.setPrefWidth(120);
		principalTextField = new TextField();
		principalLabel.setTextAlignment(TextAlignment.RIGHT);
		principalTextField.setPrefColumnCount(10);
		principalTextField.setPromptText("Principal");
		HBox principalBox = new HBox(principalLabel, principalTextField);

		Label yearLabel = new Label("Year");
		yearLabel.setPrefWidth(120);
		yearTextField = new TextField();
		yearTextField.setPrefColumnCount(10);
		yearTextField.setPromptText("Year");
		HBox yearBox = new HBox(yearLabel, yearTextField);

		Label interestLabel = new Label("Interest Rate");
		interestLabel.setPrefWidth(120);
		interestTextField = new TextField();
		interestTextField.setPrefColumnCount(8);
		interestTextField.setPromptText("Interest Rate");
		HBox interestBox = new HBox(interestLabel, interestTextField);

		Label monthlyPaymentLabel = new Label("Monthly Payment");
		monthlyPaymentLabel.setPrefWidth(120);
		monthlyPaymentTextField = new TextField();
		monthlyPaymentTextField.setPrefColumnCount(8);
		monthlyPaymentTextField.setEditable(false);
		monthlyPaymentTextField.setDisable(true);
		HBox monthlyPaymentBox = new HBox(monthlyPaymentLabel, monthlyPaymentTextField);
		monthlyPaymentTextField.setStyle("-fx-text-fill:green;-fx-font-weight: bold;");

		Label totalAmountPayableLabel = new Label("Total Amount Payable");
		totalAmountPayableLabel.setPrefWidth(120);
		totalAmountPayableTextField = new TextField();
		totalAmountPayableTextField.setPrefColumnCount(8);
		totalAmountPayableTextField.setEditable(false);
		totalAmountPayableTextField.setDisable(true);
		HBox totalAmountPayableBox = new HBox(totalAmountPayableLabel, totalAmountPayableTextField);
		totalAmountPayableTextField.setStyle("-fx-text-fill:green;-fx-font-weight: bold;");


		calculateButton = new Button("Calculate");
		HBox buttonBox = new HBox(calculateButton);
		buttonBox.setPadding(new Insets(20,0,0,0));
		buttonBox.setAlignment(Pos.CENTER);

		calculateButton.setOnAction( e -> {
			buttonCalc();
		});

		errorLabel = new Label();
		errorLabel.setStyle("-fx-background-color: red;-fx-text-fill:white;-fx-font-weight: bold;");
		HBox errorLabelBox = new HBox(errorLabel);
		errorLabelBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(topBox, principalBox, yearBox, interestBox, monthlyPaymentBox,totalAmountPayableBox, buttonBox, errorLabelBox);
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 20, 10, 20));

		Scene sc = new Scene(vBox);

		primaryStage.setResizable(false);
		primaryStage.setScene(sc);
		primaryStage.show();

	}

	public void buttonCalc(){
		NumberFormat currencyFormat =
		         NumberFormat.getCurrencyInstance(); //Change amount to currency format

		if(!isAcceptedNumber(principalTextField) || !isAcceptedNumber(yearTextField) || !isAcceptedNumber(interestTextField)){
			resetOutput();
		} else
			if (calcLoanAmount() == 0){
				resetOutput();
		} else{
			errorLabel.setText("");
			String result = currencyFormat.format(calcLoanAmount());
			String totalPayment = currencyFormat.format(calcLoanAmount()* (Double.parseDouble(yearTextField.getText())*12));
			monthlyPaymentTextField.setDisable(false);
			monthlyPaymentTextField.setText(result);
			totalAmountPayableTextField.setDisable(false);
			totalAmountPayableTextField.setText(totalPayment);
		}
	}
	private boolean isAcceptedNumber(TextField userInput){
		try{
			Double.parseDouble(userInput.getText());
			return true;
		}catch (NumberFormatException e){
			return false;
		}
	}
	private double calcLoanAmount(){
		double principal = Double.parseDouble(principalTextField.getText());
		double years = Double.parseDouble(yearTextField.getText());
		double rate = Double.parseDouble(interestTextField.getText());
		if((principal <= 0 )|| (years <= 0 )|| (rate <= 0 )){
			return 0;
		}

		double r = (rate/12) / 100.0;
		double n = 12.0 * years;
		double loanAmounts;
		loanAmounts = (r* principal)/(1 - Math.pow((1 + r), -n));

		return loanAmounts;
	}
	private void resetOutput(){
		errorLabel.setText("Invalid Input!");
		monthlyPaymentTextField.setText("");//In case there is a previous content, clear that.
		totalAmountPayableTextField.setText("");
		monthlyPaymentTextField.setDisable(true);
		totalAmountPayableTextField.setDisable(true);
	}

}