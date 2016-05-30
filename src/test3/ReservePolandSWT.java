package test3;

import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;




public class ReservePolandSWT {

	protected Shell shell;
	private Text text;
	private Table varTable;
	private Text resultText;
	private Table resultTable;
	private MessageBox messageBox;
	private TableEditor editor;
	private String result = "";
	private File sourceFile = null;
	private ReservePoland mOperation;
	private String analysisString;
	String compValueString = new String();
	private int varTableRow = 0;
	private char[] varTableVar;
	private char[] exitChar;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ReservePolandSWT window = new ReservePolandSWT();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(500, 554);
		shell.setText("逆波兰表达式的产生及计算");

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		
		
		
		
		MenuItem menuItem_2 = new MenuItem(menu, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					analysis();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		menuItem_2.setText("\u5206\u6790");
		
		
		MenuItem menuItem_6 = new MenuItem(menu, SWT.NONE);
		menuItem_6.setText("\u8BA1\u7B97");
		menuItem_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					compValue();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		

		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 10, 202, 119);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		text = new Text(scrolledComposite, SWT.BORDER);
		scrolledComposite.setContent(text);
		scrolledComposite
				.setMinSize(text.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		varTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		varTable.setBounds(218, 10, 127, 119);
		varTable.setHeaderVisible(true);
		varTable.setLinesVisible(true);
		editor = new TableEditor(varTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		createEditorContents();
		TableColumn tableColumn = new TableColumn(varTable, SWT.NONE);
		tableColumn.setWidth(61);
		tableColumn.setText("\u53D8\u91CF");
		TableColumn tblclmnNewColumn = new TableColumn(varTable, SWT.NONE);
		tblclmnNewColumn.setWidth(61);
		tblclmnNewColumn.setText("\u503C");
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(shell,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBounds(351, 10, 123, 119);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		resultText = new Text(scrolledComposite_1, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.CANCEL);
		
		resultText.setEditable(false);
		scrolledComposite_1.setContent(resultText);
		scrolledComposite_1.setMinSize(resultText.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		resultTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		resultTable.setBounds(10, 135, 464, 356);
		resultTable.setHeaderVisible(true);
		resultTable.setLinesVisible(true);
		TableColumn tblclmnNewColumn_1 = new TableColumn(resultTable, SWT.NONE);
		tblclmnNewColumn_1.setResizable(false);
		tblclmnNewColumn_1.setWidth(59);
		tblclmnNewColumn_1.setText("\u6B65\u9AA4");
		TableColumn tblclmnNewColumn_2 = new TableColumn(resultTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("\u5F53\u524D\u7B26\u53F7");
		TableColumn tblclmnNewColumn_3 = new TableColumn(resultTable, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("\u8F93\u5165\u533A");
		TableColumn tblclmnNewColumn_4 = new TableColumn(resultTable, SWT.NONE);
		tblclmnNewColumn_4.setWidth(100);
		tblclmnNewColumn_4.setText("\u8FD0\u7B97\u7B26\u53F7\u6808");
		TableColumn tblclmnNewColumn_5 = new TableColumn(resultTable, SWT.NONE);
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("\u8F93\u51FA\u533A");

	}

	

	

	

	public void analysis() {
		mOperation = new ReservePoland();
		this.resultTable.removeAll();
		this.varTable.removeAll();
		this.resultText.setText("");
		this.analysisString = text.getText();
		this.result = "";
		boolean flag = false;
		if (mOperation.checkString(this.analysisString)) {
			flag = true;
		}
		if (this.analysisString.equals("")) {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("请输入需要分析的字符串或打开需要分析的文件！");
			messageBox.setText("提示");
			messageBox.open();
		} else if (flag) {
			if (analysisString.charAt(analysisString.length() - 1) != '#') {
				analysisString += "#";
			}
			this.result = "";
			mOperation.RPNAnalysis(this.analysisString);
			for (int i = 0; i < mOperation.getRow(); i++) {
				TableItem item = new TableItem(resultTable, SWT.NULL);
				for (int j = 0; j < 5; j++) {
					item.setText(j, mOperation.getDataStrings()[i][j]);
				}
			}
			this.result = mOperation.getResult();
			showVariable();
		} else {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("输入的字符串有错误！");
			messageBox.setText("提示");
			messageBox.open();
		}
	}

	public void showVariable() {
		for (int i = 0; i < analysisString.length(); i++) {
			if (mOperation.isLetter(analysisString.charAt(i))
					|| mOperation.isDigit(analysisString.charAt(i))) {
				varTableRow++;
			}
		}
		varTableVar = new char[varTableRow];
		varTableRow = 0;
		for (int i = 0; i < analysisString.length(); i++) {
			if (mOperation.isLetter(analysisString.charAt(i))
					|| mOperation.isDigit(analysisString.charAt(i))) {
				varTableVar[varTableRow] = analysisString.charAt(i);
				varTableRow++;
			}
		}
		boolean flag = true;
		for (int i = 0; i < varTableRow; i++) {
			for (int j = 0; j < varTable.getItems().length; j++) {
				if (varTableVar[i] == varTable.getItems()[j].getText()
						.charAt(0)) {
					flag = false;
				}
			}
			if (flag) {
				TableItem item = new TableItem(varTable, SWT.NULL);
				item.setText(0, String.valueOf(varTableVar[i]));
				if (mOperation.isDigit(varTableVar[i])) {
					item.setText(1, String.valueOf(varTableVar[i]));
				} else {
					item.setText(1, "");
				}
			}
			flag = true;
		}
	}

	public void createEditorContents() {
		varTable.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				Control old = editor.getEditor();
				if (old != null) {
					old.dispose();
				}
				Point point = new Point(event.x, event.y);
				final TableItem item = varTable.getItem(point);
				if (item != null) {
					int column = -1;
					for (int i = 1, n = varTable.getColumnCount(); i < n; i++) {
						Rectangle rectangle = item.getBounds(i);
						if (rectangle.contains(point)) {
							column = i;
							break;
						}
					}
					if (column >= 0) {
						final Text text = new Text(varTable, SWT.NONE);
						text.setText(item.getText(column));
						text.selectAll();
						text.setFocus();
						editor.setEditor(text, item, column);
						final int col = column;
						text.addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent arg0) {
								item.setText(col, text.getText());
							}
						});
					}
				}
			}
		});
	}

	public char setValue(char var) {
		for (int i = 0; i < varTable.getItems().length; i++) {
			if (var == varTable.getItems()[i].getText().charAt(0)) {
				return varTable.getItems()[i].getText(1).charAt(0);
			}
		}
		return var;
	}

	public void compValue() {
		if (result == "") {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("计算失败！请先分析！");
			messageBox.setText("提示");
			messageBox.open();
		} else {
			exitChar = new char[mOperation.getExitChar().length];
			exitChar = mOperation.getExitChar();
			for (int i = 0; i < exitChar.length; i++) {
				exitChar[i] = setValue(exitChar[i]);
			}
			mOperation.setExitChar(exitChar);
			mOperation.compValue();
			compValueString = mOperation.getCompValue();
			System.out.print(compValueString);
			resultText.setText(compValueString);
		}
	}

}