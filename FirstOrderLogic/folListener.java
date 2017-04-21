// Generated from C:/My program/eclipse/workspace/FirstOrderLogic/src\fol.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link folParser}.
 */
public interface folListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code negation}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterNegation(folParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negation}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitNegation(folParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code conjunction}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(folParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conjunction}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(folParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code disjunction}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(folParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code disjunction}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(folParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code implication}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterImplication(folParser.ImplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code implication}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitImplication(folParser.ImplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atom}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterAtom(folParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atom}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitAtom(folParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesis}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterParenthesis(folParser.ParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesis}
	 * labeled alternative in {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitParenthesis(folParser.ParenthesisContext ctx);
}