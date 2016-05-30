package test3;

public class ReservePoland {

	private char[] expChar = new char[100];// 表达式栈
	private int expPointer = 0;// 表达式栈指针
	private char[] ariStack = new char[100];// 运算符栈
	private int top = 0;// 运算符栈指针
	private char[] exitChar = new char[100];// 输出栈
	private int exitPointer = 0;// 输出栈指针
	private String[][] dataStrings = new String[1000][5];// 表格数据
	private String tempString = new String();// 临时字符串
	private String result = new String();// 结果字符串
	private int row = 0;// 表格数据行
	private String compValue = new String();// 计算值
	private final char[] Arithmetic = { '+', '-', '*', '/', '(', ')', '#' };// 运算符
	private final char[] Letter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };// 字母表
	private final char[] Digit = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'0' };// 数字表

	public ReservePoland() {
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 5; j++) {
				dataStrings[i][j] = new String();
				dataStrings[i][j] = "";
			}
		}
	}

	// 判断字符是否为字母
	public boolean isLetter(char word) {
		int N = 0;
		while (N < Letter.length) {
			if (word == Letter[N]) {
				return true;
			}
			N++;
		}
		return false;
	}

	// 判断字符是否为数字
	public boolean isDigit(char word) {
		int N = 0;
		while (N < Digit.length) {
			if (word == Digit[N]) {
				return true;
			}
			N++;
		}
		return false;
	}

	// 判断字符是否为算数运算符
	public boolean isArithmetic(char word) {
		int N = 0;
		while (N < Arithmetic.length) {
			if (word == Arithmetic[N]) {
				return true;
			}
			N++;
		}
		return false;
	}

	// 输出剩余字符串
	public void printExpChar() {
		tempString = new String();
		for (int i = expPointer; i < expChar.length; i++) {
			tempString += expChar[i];
			System.out.print(expChar[i]);
			result += expChar[i];
		}
		System.out.print("\t\t");
		result += "\t\t";
	}

	// 输出符号栈
	public void printAriStack() {
		tempString = new String();
		for (int i = 0; i < top; i++) {
			tempString += ariStack[i];
			System.out.print(ariStack[i]);
			result += ariStack[i];
		}
		System.out.print("\t\t");
		result += "\t\t";
	}

	// 输出逆波兰式
	public void printExitChar() {
		tempString = new String();
		for (int i = 0; i < exitPointer; i++) {
			tempString += exitChar[i];
			System.out.print(exitChar[i]);
			result += exitChar[i];
		}
		System.out.print("\r\n");
		result += "\r\n";
	}

	// 检查字符
	public boolean checkString(String checkString) {
		char ch;
		for (int i = 0; i < checkString.length(); i++) {
			ch = checkString.charAt(i);
			if (isLetter(ch)) {
				continue;
			} else if (isDigit(ch)) {
				continue;
			} else if (isArithmetic(ch)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	// 逆波兰分析
	public void RPNAnalysis(String expression) {
		expChar = expression.toCharArray();
		char ch = expChar[expPointer++];
		int temp = 0;
		if (checkString(expression)) {
			System.out.print("步骤\t当前符号\t输入区\t\t运算符号栈\t\t输出区\r\n");
			result += "步骤\t当前符号\t输入区\t\t运算符号栈\t\t输出区\r\n";
			while (ch != '#') {
				if (isArithmetic(ch)) {
					switch (ch) {
					case '(':
						ariStack[top++] = ch;
						System.out.print(row + "\t" + ch + "\t");
						result += row + "\t" + ch + "\t";
						dataStrings[row][0] = row + "";
						dataStrings[row][1] = String.valueOf(ch);
						printExpChar();
						dataStrings[row][2] = tempString;
						printAriStack();
						dataStrings[row][3] = tempString;
						printExitChar();
						dataStrings[row][4] = tempString;
						row++;
						break;
					case ')':
						while (ariStack[--top] != '(') {
							exitChar[exitPointer++] = ariStack[top];
							System.out.print(row + "\t" + ch + "\t");
							result += row + "\t" + ch + "\t";
							dataStrings[row][0] = row + "";
							dataStrings[row][1] = String.valueOf(ch);
							printExpChar();
							dataStrings[row][2] = tempString;
							printAriStack();
							dataStrings[row][3] = tempString;
							printExitChar();
							dataStrings[row][4] = tempString;
							row++;
						}
						System.out.print(row + "\t" + ch + "\t");
						dataStrings[row][0] = row + "";
						result += row + "\t" + ch + "\t";
						dataStrings[row][1] = String.valueOf(ch);
						printExpChar();
						dataStrings[row][2] = tempString;
						printAriStack();
						dataStrings[row][3] = tempString;
						printExitChar();
						dataStrings[row][4] = tempString;
						row++;
						break;
					case '+':
					case '-':
						temp = top - 1;
						if (temp >= 0
								&& (ariStack[temp] == '*' || ariStack[temp] == '/')) {
							exitChar[exitPointer++] = ariStack[temp];
							top--;
						} else if (temp >= 0
								&& (ariStack[temp] == '-' || ariStack[temp] == '+')
								&& (expChar[expPointer + 1] != '*' || expChar[expPointer + 1] != '/')) {
							exitChar[exitPointer++] = ariStack[temp];
							top--;
						}
						while (top < 0 && ariStack[temp] != '(') {
							exitChar[exitPointer++] = ariStack[temp];
							top--;
						}
						ariStack[top++] = ch;
						System.out.print(row + "\t" + ch + "\t");
						result += row + "\t" + ch + "\t";
						dataStrings[row][0] = row + "";
						dataStrings[row][1] = String.valueOf(ch);
						printExpChar();
						dataStrings[row][2] = tempString;
						printAriStack();
						dataStrings[row][3] = tempString;
						printExitChar();
						dataStrings[row][4] = tempString;
						row++;
						break;
					case '*':
					case '/':
						temp = top - 1;
						if (temp >= 0
								&& (ariStack[temp] == '*' || ariStack[temp] == '/')) {
							exitChar[exitPointer++] = ariStack[temp];
							top--;
						}
						ariStack[top++] = ch;
						System.out.print(row + "\t" + ch + "\t");
						result += row + "\t" + ch + "\t";
						dataStrings[row][0] = row + "";
						dataStrings[row][1] = String.valueOf(ch);
						printExpChar();
						dataStrings[row][2] = tempString;
						printAriStack();
						dataStrings[row][3] = tempString;
						printExitChar();
						dataStrings[row][4] = tempString;
						row++;
						break;
					default:
						break;
					}
				} else if (isLetter(ch) || isDigit(ch)) {
					exitChar[exitPointer++] = ch;
					System.out.print(row + "\t" + ch + "\t");
					result += row + "\t" + ch + "\t";
					dataStrings[row][0] = row + "";
					dataStrings[row][1] = String.valueOf(ch);
					printExpChar();
					dataStrings[row][2] = tempString;
					printAriStack();
					dataStrings[row][3] = tempString;
					printExitChar();
					dataStrings[row][4] = tempString;
					row++;
				}
				ch = expChar[expPointer++];
			}
			while (top != 0) {
				temp = top - 1;
				if (ariStack[temp] == '(') {
					top--;
					continue;
				}
				exitChar[exitPointer++] = ariStack[--top];
				System.out.print(row + "\t" + ch + "\t");
				result += row + "\t" + ch + "\t";
				dataStrings[row][0] = row + "";
				dataStrings[row][1] = String.valueOf(ch);
				printExpChar();
				dataStrings[row][2] = tempString;
				printAriStack();
				dataStrings[row][3] = tempString;
				printExitChar();
				dataStrings[row][4] = tempString;
				row++;
			}
		}
	
	}
	// 计算值
		public void compValue() {
			int stack[] = new int[100];
			exitChar[exitPointer++] = '#';
			char ch;
			int i = 0, top = 0;
			ch = exitChar[i++];
			while (ch != '#') {
				switch (ch) {
				case '+':
					compValue += stack[top - 1] + " + " + stack[top];
					stack[top - 1] = stack[top - 1] + stack[top];
					compValue += " := " + stack[top - 1] + "\r\n";
					top--;
					break;
				case '-':
					compValue += stack[top - 1] + " - " + stack[top];
					stack[top - 1] = stack[top - 1] - stack[top];
					compValue += " := " + stack[top - 1] + "\r\n";
					top--;
					break;
				case '*':
					compValue += stack[top - 1] + " * " + stack[top];
					stack[top - 1] = stack[top - 1] * stack[top];
					compValue += " := " + stack[top - 1] + "\r\n";
					top--;
					break;
				case '/':
					compValue += stack[top - 1] + " / " + stack[top];
					stack[top - 1] = stack[top - 1] / stack[top];
					compValue += " := " + stack[top - 1] + "\r\n";
					top--;
					break;
				default:
					top++;
					stack[top] = ch - '0';
					break;
				}
				ch = exitChar[i++];
			}
			compValue += "结果：" + stack[top];
			System.out.print(compValue);
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public String[][] getDataStrings() {
			return dataStrings;
		}

		public void setDataStrings(String[][] dataStrings) {
			this.dataStrings = dataStrings;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public char[] getExitChar() {
			return exitChar;
		}

		public void setExitChar(char[] exitChar) {
			this.exitChar = exitChar;
		}

		public String getCompValue() {
			return compValue;
		}

		public void setCompValue(String compValue) {
			this.compValue = compValue;
		}

	}