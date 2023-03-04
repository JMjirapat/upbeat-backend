package Parser;

import AST.PlanNode;

interface Parser {
    /**
     * Attempts to parse the token stream
     * given to this parser.
     * throws: SyntaxError if the token
     * stream cannot be parsed
     */
    PlanNode parse();
}

