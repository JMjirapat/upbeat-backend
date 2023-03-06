package Parser;

import AST.Node.ExecNode;

interface Parser {
    /**
     * Attempts to parse the token stream
     * given to this parser.
     * throws: SyntaxError if the token
     * stream cannot be parsed
     */
    ExecNode parse();
}

