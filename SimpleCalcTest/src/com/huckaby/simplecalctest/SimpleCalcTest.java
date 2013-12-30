package com.huckaby.simplecalctest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

public class SimpleCalcTest extends Activity {
	private TextView operandTextView;
	private TextView opCurrentTextView;
	private String operand1;
	private String operandWorking;
	private String operand2;
	private String result;
	private String operator;
	
	private int inputState;
	private final int INPUT_STATE_BEGIN_OPERAND = 1;
	private final int INPUT_STATE_CONTINUE_OPERAND = 2;
	
	private int operationState;
	private final int OPERATION_STATE_OPERAND_1 = 1;
	private final int OPERATION_STATE_OPERAND_2 = 2;
	private final int OPERATION_STATE_RESULT = 3;
	
	private final int FUNC_CLEAR_ALL = 1;
	private final int FUNC_CLEAR_ENTRY = 2;
	
	//private final int[] numButtonIds = new int[11];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_calc_test);
		
		operandTextView = (TextView)findViewById(R.id.calcText);
		opCurrentTextView = (TextView)findViewById(R.id.opCurrent);
		operandWorking = "0";
		operandTextView.setText(operandWorking);
		inputState = INPUT_STATE_BEGIN_OPERAND;
		operationState = OPERATION_STATE_OPERAND_1;
		
//		numButtonIds[0] = findViewById(R.id.button0).getId();
//		numButtonIds[1] = findViewById(R.id.button1).getId();
//		numButtonIds[2] = findViewById(R.id.button2).getId();
//		numButtonIds[3] = findViewById(R.id.button3).getId();
//		numButtonIds[4] = findViewById(R.id.button4).getId();
//		numButtonIds[5] = findViewById(R.id.button5).getId();
//		numButtonIds[6] = findViewById(R.id.button6).getId();
//		numButtonIds[7] = findViewById(R.id.button7).getId();
//		numButtonIds[8] = findViewById(R.id.button8).getId();
//		numButtonIds[9] = findViewById(R.id.button9).getId();
//		numButtonIds[10] = findViewById(R.id.buttonDecimal).getId();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple_calc_test, menu);
		return true;
	}
	
	private double evaluate() {
		Log.i("operand1", operand1);
		Log.i("operand2", operand2);
		double op1 = Double.parseDouble(operand1);
		double op2 = Double.parseDouble(operand2);
		
		double result = 0.0;
		
		if (operator == "+") {
			result = op1 + op2;
		}
		else if (operator == "-") {
			result = op1 - op2;
		}
		else if (operator == "*") {
			result = op1 * op2;
		}
		else if (operator == "/") {
			if (op2 == 0) {
				result = 0;
			}
			else {
				result = op1/op2;
			}
		}
		
		return result;
	}
	
	private void clear(int clearType) {
		if (clearType == FUNC_CLEAR_ALL) {
			operationState = OPERATION_STATE_OPERAND_1;
			inputState = INPUT_STATE_BEGIN_OPERAND;
			operand1 = "0";
			operand2 = "0";
			operandWorking = "0";
			operandTextView.setText(operandWorking);
			opCurrentTextView.setText("");
		}
		else if (clearType == FUNC_CLEAR_ENTRY) {
			if (operationState != OPERATION_STATE_RESULT) {
				inputState = INPUT_STATE_BEGIN_OPERAND;
				operandWorking = "0";
				operandTextView.setText(operandWorking);
			}
			else {
				operationState = OPERATION_STATE_OPERAND_2;
				operandWorking = "0";
				operand2 = "0";
				inputState = INPUT_STATE_BEGIN_OPERAND;
			}
		}
	}
	
	public void numberClick(View button) {
		int buttonID = ((Button)button).getId();
		String numStr;
		
		if (operationState == OPERATION_STATE_RESULT){
			clear(FUNC_CLEAR_ALL);
		}
		
		if (buttonID == findViewById(R.id.button0).getId())
			numStr = "0";
		else if (buttonID == findViewById(R.id.button1).getId())
			numStr = "1";
		else if (buttonID == findViewById(R.id.button2).getId())
			numStr = "2";
		else if (buttonID == findViewById(R.id.button3).getId())
			numStr = "3";
		else if (buttonID == findViewById(R.id.button4).getId())
			numStr = "4";
		else if (buttonID == findViewById(R.id.button5).getId())
			numStr = "5";
		else if (buttonID == findViewById(R.id.button6).getId())
			numStr = "6";
		else if (buttonID == findViewById(R.id.button7).getId())
			numStr = "7";
		else if (buttonID == findViewById(R.id.button8).getId())
			numStr = "8";
		else if (buttonID == findViewById(R.id.button9).getId())
			numStr = "9";
		else if (buttonID == findViewById(R.id.buttonDecimal).getId())
			numStr = ".";
		else numStr = "";
		
		if (inputState == INPUT_STATE_BEGIN_OPERAND) {
			operandWorking = numStr;
			operandTextView.setText(operandWorking);
			inputState = INPUT_STATE_CONTINUE_OPERAND;
		}
		else if (inputState == INPUT_STATE_CONTINUE_OPERAND
				&& operandWorking.length() <= 11) {
			operandWorking = operandWorking.concat(numStr);
			operandTextView.setText(operandWorking);
		}
	}
	
	public void operatorClick(View button) {
		if (operationState == OPERATION_STATE_OPERAND_1) {
			operationState = OPERATION_STATE_OPERAND_2;
			inputState = INPUT_STATE_BEGIN_OPERAND;
			operand1 = operandWorking;
			operandWorking = "0";
			
			int buttonID = ((Button)button).getId();
			
			if (buttonID == findViewById(R.id.buttonAdd).getId())
				operator = "+";
			else if (buttonID == findViewById(R.id.buttonSubtract).getId())
				operator = "-";
			else if (buttonID == findViewById(R.id.buttonDivide).getId())
				operator = "/";
			else if (buttonID == findViewById(R.id.buttonMultiply).getId())
				operator = "*";
			
			opCurrentTextView.setText(operand1.concat(" ").concat(operator));
		}
		else if (operationState == OPERATION_STATE_OPERAND_2) {
			operand2 = operandWorking;
			inputState = INPUT_STATE_BEGIN_OPERAND;
			result = Double.toString(evaluate());
			operand1 = result;
			operandWorking = "0";
			operandTextView.setText(result);
			
			int buttonID = ((Button)button).getId();
			
			if (buttonID == findViewById(R.id.buttonAdd).getId())
				operator = "+";
			else if (buttonID == findViewById(R.id.buttonSubtract).getId())
				operator = "-";
			else if (buttonID == findViewById(R.id.buttonDivide).getId())
				operator = "/";
			else if (buttonID == findViewById(R.id.buttonMultiply).getId())
				operator = "*";
			
			operandTextView.setText(result);
			opCurrentTextView.setText(operand1.concat(" ").concat(operator));
		}
		else if (operationState == OPERATION_STATE_RESULT) {
			operationState = OPERATION_STATE_OPERAND_2;
			inputState = INPUT_STATE_BEGIN_OPERAND;
			operand1 = result;
			operandWorking = "0";
			
			int buttonID = ((Button)button).getId();
			
			if (buttonID == findViewById(R.id.buttonAdd).getId())
				operator = "+";
			else if (buttonID == findViewById(R.id.buttonSubtract).getId())
				operator = "-";
			else if (buttonID == findViewById(R.id.buttonDivide).getId())
				operator = "/";
			else if (buttonID == findViewById(R.id.buttonMultiply).getId())
				operator = "*";
			
			opCurrentTextView.setText(operand1.concat(" ").concat(operator));
		}
		
		
	}
	
	public void functionClick(View button) {
		int buttonID = ((Button)button).getId();
		
		if (buttonID == findViewById(R.id.buttonCE).getId())
			clear(FUNC_CLEAR_ENTRY);
		else if (buttonID == findViewById(R.id.buttonC).getId())
			clear(FUNC_CLEAR_ALL);
		else if (buttonID == findViewById(R.id.buttonBack).getId()) {
			if (operandWorking.length() == 1) {
				inputState = INPUT_STATE_BEGIN_OPERAND;
				operandWorking = "0";
			}
			else {
				operandWorking = operandWorking.substring(0, operandWorking.length()-1);
			}
			operandTextView.setText(operandWorking);
		}
		else if (buttonID == findViewById(R.id.buttonEquals).getId())
			if (operationState == OPERATION_STATE_OPERAND_2) {
				operationState = OPERATION_STATE_RESULT;
				opCurrentTextView.setText("");
				operand2 = operandWorking;
				result = Double.toString(evaluate());
				operandTextView.setText(result);
			};
		
	}

}
