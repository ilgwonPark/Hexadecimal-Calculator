import java.util.*;

import java.util.regex.Pattern;

public class Calculator {

	private String calculation;

	private CompuList<String> itemList = null;

	/** Creates a new instance of Main */

	public Calculator(String calculation) {

		this.calculation = calculation;

	}

	// ---------------------------main
	// method------------------------------------//

	public static void main(String[] args) {

		try {

			System.out.println("\nWhat do you want to calculate ?? :-) ");

			Scanner scan = new Scanner(System.in); // receive formula from
													// console

			String input = scan.next();

			Calculator main = new Calculator(input); // use formula as a
														// parameter

			long wow = main.computation();

			// print only result is positive because it is printed the value if
			// it is negative

			if (wow > 0) {

				String output = Long.toHexString(wow).toUpperCase();

				System.out.println("\nresult: " + output);

			} else if (wow < 0) {

				System.out.println("\nResult: " + "-" + Long.toHexString(Math.abs(wow)).toUpperCase());

			}

		}

		catch (Exception e) {

			System.out.println("\nIt is not appropriate formula!!");

		}

	}

	// ---------------------------Operator
	// eum------------------------------------//

	enum Operator {

		PLUS(1, "+"), MINUS(1, "-"), MULTIPLICATION(2, "*"), DIVISION(2, "/"), LEFTPR(

				0, "("), RIGHTPR(0, ")");

		int priority; // smaller num means higher priority

		String opCh;

		Operator(int priority, String operator) { // initialize the Operator
													// with constructor

			this.priority = priority;

			this.opCh = operator;

		}

		long calc(long operand1, long operand2) {

			long result;

			if (this == PLUS) {

				result = operand2 + operand1;

			} else if (this == MULTIPLICATION) {

				result = operand2 * operand1;

			} else if (this == DIVISION) {

				result = operand2 / operand1;

			} else if (this == MINUS) {

				result = operand2 - operand1;

			} else {

				throw new Error("invalid operator");

			}

			return result;

		}
	}

	// ---------------------------makePostFixList()------------------------------------

	private void makePFList() { // make postFixList with item

		Stack<Operator> opStack = new Stack<Operator>();

		itemList = new CompuList<String>();

		for (String item : operators()) {

			if (isOperand(item)) {

				itemList.add(item);

			} else {

				Operator op = getOperator(item);

				if (op == Operator.RIGHTPR) {

					popOpsInParenthesis(opStack);

				} else {

					if (op != Operator.LEFTPR) {

						while (!opStack.isEmpty() // put high priority operator

								&& (opStack.peek().priority >= op.priority)) {

							itemList.add(opStack.pop());

						}
					}

					opStack.push(op);

				}
			}
		}

		while (!opStack.empty()) {

			itemList.add(opStack.pop());

		}
	}

	// ---------------------------isOperand()------------------------------------

	// check the operand whether it is between 0-9, A-F, a-f

	public boolean isOperand(String token) {

		return Pattern.matches("[0-9A-Fa-f]+", token);

	}

	// ---------------------------opeartors()------------------------------------

	private List<String> operators() {

		StringTokenizer stk = new StringTokenizer(calculation, "()*/+-", true);

		List<String> itemList = new ArrayList<String>(); // put operands except
															// for operators

		while (stk.hasMoreTokens()) {

			itemList.add(stk.nextToken());

		}

		return itemList;

	}

	// ---------------------------getOperator()------------------------------------

	private Operator getOperator(String item) {

		try {

			for (Operator op : Operator.values()) {

				if (op.opCh.equals(item)) // get from enum

					return op;

			}
		} catch (Exception e) {

			System.out.println("Error!!");

		}

		return null;

	}

	// ---------------------------camputation()------------------------------------

	public long computation() {

		Operator op = null;

		if (itemList == null) {

			makePFList();

		}

		Stack<Long> numStack = new Stack<Long>();

		for (String item : itemList) {

			if (isOperand(item)) {

				numStack.push((Long.parseLong(item, 16))); // put the item
															// changed to
															// hexadecimal Long

			} else {

				op = getOperator(item); // or put the opreator

				numStack.push(op.calc(numStack.pop(), numStack.pop()));

			}

		}

		return numStack.pop();

	}

	// ---------------------------popOpsInParenthesis()------------------------------------

	private void popOpsInParenthesis(Stack<Operator> opStack) {

		if (!opStack.contains(Operator.LEFTPR))

			throw new Error("invalid formula: LPR required");

		// pop every operators inside parenthesis

		while (opStack.peek() != Operator.LEFTPR) {

			itemList.add(opStack.pop());

		}

		itemList.add(opStack.pop()); // remove lpr from op stack

	}

	// ---------------------------CompuList
	// class------------------------------------

	class CompuList<T> extends ArrayList<String> {

		public void add(Operator op) {

			if (op != Calculator.Operator.LEFTPR

					&& op != Calculator.Operator.RIGHTPR) { // store operator
															// not parenthesis

				add(op.opCh);

			}

		}

	}

}