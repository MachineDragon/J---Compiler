// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.GOTO;

/**
 * An AST node for a do-while-statement.
 */
public class JDoStatement extends JStatement {
    // The body.
    private JStatement body;

    // Test expression.
    private JExpression condition;

    /**
     * Constructs an AST node for a do-while-statement.
     *
     * @param line      line in which the do-while-statement occurs in the source file.
     * @param body      the body.
     * @param condition test expression.
     */
    public JDoStatement(int line, JStatement body, JExpression condition) {
        super(line);
        this.body = body;
        this.condition = condition;
    }

    /**
     * {@inheritDoc}
     */
    public JDoStatement analyze(Context context) {
        // analyze the condition
        condition = (JExpression) condition.analyze(context);

        // make sure its a boolean
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);

        // analyze the body
        body = (JStatement) body.analyze(context);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
            // Creates a top and bottom for our loop
            String topLabel = output.createLabel();
            String bottomLabel = output.createLabel();

            // Start the loop
            output.addLabel(topLabel);

            // body code
            body.codegen(output);

            // 3-argument codegen
            // Leave the loop if condition is false
            condition.codegen(output, bottomLabel, false);

            // go to top label if true
            // code still works without this line
            condition.codegen(output, topLabel, true);

            // Start over from top label if not false and repeat loop
            output.addBranchInstruction(GOTO, topLabel);

            // Label for GOTO reference to jump to when checking condition
            output.addLabel(bottomLabel);
            }


    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JDoStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Body", e1);
        body.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("Condition", e2);
        condition.toJSON(e2);
    }
}
