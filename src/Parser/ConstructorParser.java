package Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Tokenizer.Tokenizer;
import AST.*;
import Game.Direction;

import java.util.function.Function;
import java.util.function.Predicate;


public class ConstructorParser implements Parser{

    private final Tokenizer tokenizer;

    private final List<String> commands = Arrays.asList("done", "relocate", "move", "invest", "collect", "shoot");

    private final List<String> reserved = Arrays.asList("collect", "done", "down", "downleft", "downright", "else", "if", "invest", "move", "nearby", "opponent", "relocate", "shoot", "then", "up", "upleft", "upright", "while");

    ConstructorParser(Tokenizer tkz){
        if (!tkz.hasNextToken())
            throw new StatementRequired(tkz.getLine());
        this.tokenizer = tkz;
    }

    private boolean elem(String x, List<String> list){
        Predicate<String> equalsX = a -> a.equals(x);
        return list.stream().anyMatch(equalsX);
    }

    public static Predicate<String> isNumber = str -> str.matches("\\d+");

    public static Predicate<String> isString = str -> str.matches("[a-zA-Z]+");

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

    @Override
    public PlanNode parse() throws SyntaxError, LexicalError {
        PlanNode plan = parseStatements.apply(tokenizer);
        // reject if there is remaining token
        if (tokenizer.hasNextToken())
            throw new SyntaxError("leftover token");
        return plan;
    }

    //    Plan → Statement+
    private Function<Tokenizer,PlanNode> parseStatements = tkz -> {
        ArrayList<PlanNode> statements = new ArrayList<>();
        while (tkz.hasNextToken()) {
            statements.add(parseStatement());
        }
        return new StatementsEvaluator(statements);
    };

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
        if(elem(tkz.peek(),commands)){
            return parseActionCommand();
        }else{
            return parseAssignmentStatement();
        }
    }

    //    AssignmentStatement → <identifier> = Expression
    private PlanNode parseAssignmentStatement() throws LexicalError, SyntaxError {
        if (elem(tkz.peek(),reserved))
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
            return new RelocateNode();
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
        if(tkz.peek("}")){
            tkz.consume("}");
            return new EmptyNode();
        }
        PlanNode s = parseStatements();
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
        return new IfElseEvaluator(expression,ts,fs);
    }

    //    WhileStatement → while ( Expression ) Statement
    private PlanNode parseWhileStatement() throws LexicalError, SyntaxError {
        tkz.consume("while");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        PlanNode s = parseStatement();
        return new WhileEvaluator(expression,s);
    }

    //    Expression → Expression + Term | Expression - Term | Term // T ((+T) | (-T))*
    private ExprNode parseExpression() throws SyntaxError, LexicalError {
        ExprNode v = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            if(tkz.peek("+")){
                tkz.consume();
                v = new ArithmeticEvaluator(v, "+", parseTerm());
            }else if(tkz.peek("-")){
                tkz.consume();
                v = new ArithmeticEvaluator(v, "-", parseTerm());
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
                v = new ArithmeticEvaluator(v, "*", parseFactor());
            }else if(tkz.peek("/")){
                tkz.consume();
                v = new ArithmeticEvaluator(v, "/", parseFactor());
            }else if(tkz.peek("%")){
                tkz.consume();
                v = new ArithmeticEvaluator(v, "%", parseFactor());
            }
        }
        return v;
    }

    //    Factor → Power ^ Factor | Power
    private ExprNode parseFactor() throws LexicalError, SyntaxError {
        ExprNode v = parsePower();
        while (tkz.peek("^")) {
            tkz.consume();
            v = new ArithmeticEvaluator(v, "^", parseFactor());
        }
        return v;
    }

    //    Power → <number> | <identifier> | ( Expression ) | InfoExpression
    private ExprNode parsePower() throws LexicalError, SyntaxError {
        if (isNumber.test(tkz.peek())) {
            return new IntLit(Integer.parseInt(tkz.consume()));
        }else if(isString.test(tkz.peek())){
            return new Identifier(tkz.consume());
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
            return new OpponentNode();
        }else{
            tkz.consume("nearby");
            Direction direction = parseDirection();
            return new NearByNode(direction);
        }
    }

    //
//    public static boolean isNumber(String str) {
//        return str.matches("\\d+");  //match a number with optional '-' and decimal.
//    }
//
//    public static boolean isString(String str){
//        return str.matches("[a-zA-Z]+");
//    }
}
