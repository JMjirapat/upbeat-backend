import java.util.Arrays;
import java.util.List;

public class ConstructorParser implements Parser{

    private final Tokenizer tkz;
    private final List<String> commands = Arrays.asList("done", "relocate", "move", "invest", "collect", "shoot");
    private final List<String> reserved = Arrays.asList("collect", "done", "down", "downleft", "downright", "else", "if", "invest", "move", "nearby", "opponent", "relocate", "shoot", "then", "up", "upleft", "upright", "while");

    ConstructorParser(Tokenizer tkz){
        this.tkz = tkz;
    }

    //    Plan → Statement+
    //    Statement → Command | BlockStatement | IfStatement | WhileStatement
    //    Command → AssignmentStatement | ActionCommand
    //    AssignmentStatement → <identifier> = Expression
    //    ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
    //    MoveCommand → move Direction
    //    RegionCommand → invest Expression | collect Expression
    //    AttackCommand → shoot Direction Expression
    //    Direction → up | down | upleft | upright | downleft | downright
    //    BlockStatement → { Statement* }
    //    IfStatement → if ( Expression ) then Statement else Statement
    //    WhileStatement → while ( Expression ) Statement
    //    Expression → Expression + Term | Expression - Term | Term
    //    Term → Term * Factor | Term / Factor | Term % Factor | Factor
    //    Factor → Power ^ Factor | Power
    //    Power → <number> | <identifier> | ( Expression ) | InfoExpression
    //    InfoExpression → opponent | nearby Direction

    //    Plan → Statement+
    @Override
    public PlanNode parse() throws SyntaxError, LexicalError {
        PlanNode result = parseStatement();
        // reject if there is remaining token
        if (tkz.hasNextToken())
            throw new SyntaxError("leftover token");
        return result;
    }

    //    Statement → Command | BlockStatement | IfStatement | WhileStatement
    private PlanNode parseStatement() throws LexicalError, SyntaxError {
        if(tkz.peek("{")){
            return parseBlockStatement();
        }else if(tkz.peek("while")){
            return parseWhileStatement();
        }else if(tkz.peek("if")){
            return parseIfStatement();
        }else{
            return parseCommand();
        }
    }

    //    Command → AssignmentStatement | ActionCommand
    private PlanNode parseCommand() throws LexicalError, SyntaxError {
        if(commands.contains(tkz.peek())){
            return parseActionCommand();
        }else{
            return parseAssignmentStatement();
        }
    }

    //    AssignmentStatement → <identifier> = Expression
    private PlanNode parseAssignmentStatement() throws LexicalError, SyntaxError {
        if (reserved.contains(tkz.peek()))
            throw new SyntaxError("Identifier mustn't be reserved word");
        String identifier = tkz.consume();
        if(tkz.peek("=")){
            tkz.consume();
        }else{
            throw new SyntaxError("Identifier is don't match");
        }
        ExprNode expression = parseExpression();
        return new AssignmentNode(identifier,expression);
    }

    //    ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
    private PlanNode parseActionCommand() throws SyntaxError, LexicalError {
        if(tkz.peek("done")){
            return new DoneNode();
        }else if(tkz.peek("relocate")){
            return new Relocate();
        }else if(tkz.peek("move")){
            return parseMoveCommand();
        }else if(tkz.peek("invest") || tkz.peek("collect")){
            return parseRegionCommand();
        }else if(tkz.peek("shoot")){
            return parseAttackCommand();
        }else{
            throw  new SyntaxError("This command is not correct");
        }
    }

    //    MoveCommand → move Direction
    private PlanNode parseMoveCommand() throws LexicalError {
        Direction direction = parseDirection();
        return new MoveNode(direction);
    }
    //    RegionCommand → invest Expression | collect Expression
    private PlanNode parseRegionCommand() throws LexicalError, SyntaxError {
        String regionCommand = tkz.consume();
        ExprNode expression = parseExpression();
        if(regionCommand.equals("invest")){
            return new InvestNode(expression);
        }else{
            return new CollectNode(expression);
        }
    }
    //    AttackCommand → shoot Direction Expression
    private PlanNode parseAttackCommand() throws LexicalError, SyntaxError {
        Direction direction = parseDirection();
        ExprNode expression = parseExpression();
        return new AttackNode(direction, expression);
    }

    //    Direction → up | down | upleft | upright | downleft | downright
    private Direction parseDirection() throws LexicalError {
        String direction = tkz.consume();
        return switch (direction) {
            case "up" -> Direction.Up;
            case "down" -> Direction.Down;
            case "upleft" -> Direction.UpLeft;
            case "upright" -> Direction.UpRight;
            case "downleft" -> Direction.DownLeft;
            case "downright" -> Direction.DownRight;
            default -> throw new InvalidDirection(direction);
        };
    }

    //    BlockStatement → { Statement* }
    private PlanNode parseBlockStatement() throws LexicalError, SyntaxError {
        tkz.consume("{");
        PlanNode s = parseStatement();
        tkz.consume("}");
        return s;
    }

    //    IfStatement → if ( Expression ) then Statement else Statement
    private PlanNode parseIfStatement() throws LexicalError, SyntaxError {
        tkz.consume("if");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        PlanNode ts = parseStatement();
        tkz.consume("else");
        PlanNode fs = parseStatement();
        return new IfElseNode(expression,ts,fs);
    }

    //    WhileStatement → while ( Expression ) Statement
    private PlanNode parseWhileStatement() throws LexicalError, SyntaxError {
        tkz.consume("while");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        PlanNode s = parseStatement();
        return new WhileNode(expression,s);
    }

    //    Expression → Expression + Term | Expression - Term | Term // T ((+T) | (-T))*
    private ExprNode parseExpression() throws SyntaxError, LexicalError {
        ExprNode v = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            if(tkz.peek("+")){
                tkz.consume();
                v = new BinaryArithExpr(v, "+", parseTerm());
            }else if(tkz.peek("-")){
                tkz.consume();
                v = new BinaryArithExpr(v, "-", parseTerm());
            }
        }
        return v;
    }

    //    Term → Term * Factor | Term / Factor | Term % Factor | Factor // F ((*F) | (/F) | (%F))*
    private ExprNode parseTerm() throws LexicalError, SyntaxError {
        ExprNode v = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            if(tkz.peek("*")){
                tkz.consume();
                v = new BinaryArithExpr(v, "*", parseFactor());
            }else if(tkz.peek("/")){
                tkz.consume();
                v = new BinaryArithExpr(v, "/", parseFactor());
            }else if(tkz.peek("%")){
                tkz.consume();
                v = new BinaryArithExpr(v, "%", parseFactor());
            }
        }
        return v;
    }

    //    Factor → Power ^ Factor | Power
    private ExprNode parseFactor() throws LexicalError, SyntaxError {
        ExprNode v = parsePower();
        while (tkz.peek("^")) {
            tkz.consume();
            v = new BinaryArithExpr(v, "^", parseFactor());
        }
        return v;
    }

    //    Power → <number> | <identifier> | ( Expression ) | InfoExpression
    private ExprNode parsePower() throws LexicalError, SyntaxError {
        if (isNumber(tkz.peek())) {
            return new IntLit(Integer.parseInt(tkz.consume()));
        }else if(isString(tkz.peek())){
            return new Variable(tkz.consume());
        }else if(tkz.peek("(")){
            tkz.consume("(");
            ExprNode v = parseExpression();
            tkz.consume(")");
            return v;
        }else{
            return parseInfoExpression();
        }
    }

    //    InfoExpression → opponent | nearby Direction
    private ExprNode parseInfoExpression() throws LexicalError, SyntaxError {
        if(tkz.peek("opponent")){
            tkz.consume();
            return new OppNode;
        }else{
            tkz.consume("nearby");
            return new NearByNode()
        }
    }

    //
    public static boolean isNumber(String str) {
        return str.matches("\\d+");  //match a number with optional '-' and decimal.
    }

    public static boolean isString(String str){
        return str.matches("[a-zA-Z]+");
    }
}
