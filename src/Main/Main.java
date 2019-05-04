package Main;

import Gubbins.Parser.Gubbins;
import Gubbins.Parser.ParseException;
import ast.IASTStatement;
import ntree.INStatement;
import symtab.ISymTab;
import symtab.SymTab;
import types.TypeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.*;

public class Main
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws ParseException, TypeException, FileNotFoundException
	{
		File file = new File("C:\\Users\\Frank Ochoa\\IdeaProjects\\Gubbins\\src\\Main\\GrammarTest");
		Scanner fileScanner = new Scanner(file);

		StringBuilder builder =  new StringBuilder();

		while(fileScanner.hasNextLine())
		{
			builder.append(fileScanner.nextLine());
		}

		StringReader code = new StringReader(builder.toString());

		Gubbins f = new Gubbins(code);
		List<IASTStatement> topLevels = f.Input();

		ISymTab table = new SymTab();
		table.enterNewScope();
		StatementTypeChecker typeChecker = new StatementTypeChecker(table);
		//  for each AST tree produced by the parser, visit the tree and
		//  pass the type checker object as the visitor
		List<INStatement> trees = new LinkedList<>();
		for (IASTStatement tree : topLevels)
		{
			INStatement t = typeChecker.typecheck(tree);
			trees.add(t);
		}

		//System.out.println(typeChecker.getTypeEnvironment());
		//System.out.println(trees);

		ISymTab evalTable = new SymTab();
		evalTable.enterNewScope();
		StatementEvaluator statementEvaluator = new StatementEvaluator(evalTable);
		for (INStatement tree : trees)
		{
			statementEvaluator.eval(tree);
		}

	}

}
