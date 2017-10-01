package org.matheclipse.core.preprocessor;

import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.matheclipse.core.convert.AST2Expr;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.ast.ASTNode;

public class ElementPreprocessor {

	public static void main(String[] args) {
		F.initSymbols();
		FileReader reader = null;
		try {
			EvalEngine engine = EvalEngine.get();
			boolean relaxedSyntax = false;
			String fileName = "C:\\Users\\dev\\git\\symja_android_library\\symja_android_library\\tools\\src\\main\\java\\org\\matheclipse\\core\\preprocessor\\element.csv";
			reader = new FileReader(fileName);
			AST2Expr ast2Expr = new AST2Expr(relaxedSyntax, engine);
			final Parser parser = new Parser(relaxedSyntax, true);

			CSVFormat csvFormat = CSVFormat.RFC4180.withDelimiter('\t');
			Iterable<CSVRecord> records = csvFormat.parse(reader);
			IAST rowList = F.List();
			for (CSVRecord record : records) {
				IAST columnList = F.List();
				for (String str : record) {
					str = str.trim();
					if (str.length() == 0) {
						// columnList.append(F.Null);
					} else if (str.equalsIgnoreCase("Not_applicable")) {
						columnList.append(F.Missing(F.NotApplicable));
					} else if (str.equalsIgnoreCase("Not_available")) {
						columnList.append(F.Missing(F.NotAvailable));
					} else if (str.equalsIgnoreCase("Not_known")) {
						columnList.append(F.Missing(F.Unknown));
					} else {
						final ASTNode node = parser.parse(str);
						IExpr temp = ast2Expr.convert(node);
						if (temp.isList() || temp.isSignedNumber()) {
							columnList.append(temp);
						} else {
							if (str.charAt(0) == '\"') {
								columnList.append(F.stringx(str.substring(1, str.length() - 1)));
							} else {
								columnList.append(F.stringx(str));
							}
						}
					}
				}
				rowList.append(columnList);
			}
			// System.out.println(rowList.toString());
			for (int i = 2; i < rowList.size(); i++) {
				IAST columnList = (IAST) rowList.get(i);

				System.out.print(columnList.internalJavaString(false, 1, false));
				System.out.println(", ");
			}
			// return rowList;

		} catch (IOException ioe) {
			System.out.println("Import: file not found!");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
