package Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Tokenizer.Tokenizer;
import Parser.ParserException.*;
import AST.*;
import Game.Direction;

public class ConstructorParser implements Parser{

    private final Tokenizer tkz;
    private final List<String> commands = Arrays.asList("done", "relocate", "move", "invest", "collect", "shoot");
    private final List<String> reserved = Arrays.asList("collect", "done", "down", "downleft", "downright", "else", "if", "invest", "move", "nearby", "opponent", "relocate", "shoot", "then", "up", "upleft", "upright", "while");

    ConstructorParser(Tokenizer tkz){
        if (!tkz.hasNextToken())
            throw new ParserException.TokenRequired();
        this.tkz = tkz;
    }

//    public static Predicate<String> isNumber = str -> str.matches("\\d+");
//    public static Predicate<String> isString = str -> str.matches("[a-zA-Z]+");

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
    public PlanNode parse() {
        PlanNode plan = parseStatements();
        // reject if there is remaining token
        if (tkz.hasNextToken())
            throw new LeftoverToken(tkz.getLine());
        return plan;
    }

    //    Plan → Statement+
    private PlanNode parseStatements(){
        ArrayList<PlanNode> statements = new ArrayList<>();
        while (tkz.hasNextToken()) {
            statements.add(parseStatement());
        }
        return new StatementsEvaluator(statements);
    }

//    private Function<Tokenizer,PlanNode> parseStatements = tkz -> {
//        ArrayList<PlanNode> statements = new ArrayList<>();
//        while (tkz.hasNextToken()) {
//            statements.add(parseStatement.apply(tkz));
//        }
//        return new StatementsEvaluator(statements);
//    };

    //    Statement → Command | BlockStatement | IfStatement | WhileStatement
    private PlanNode parseStatement(){
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

//    private Function<Tokenizer,PlanNode> parseStatement = tkz -> {
//        if(tkz.peek("{")){
//            return parseBlockStatement();
//        }else if(tkz.peek("while")){
//            return parseWhileStatement();
//        }else if(tkz.peek("if")){
//            return parseIfStatement();
//        }else{
//            return parseCommand();
//        }
//    };

    //    Command → AssignmentStatement | ActionCommand
    private PlanNode parseCommand() {
        if(commands.contains(tkz.peek())){
            return parseActionCommand();
        }else{
            return parseAssignmentStatement();
        }
    }

    //    AssignmentStatement → <identifier> = Expression
    private PlanNode parseAssignmentStatement() {
        if (reserved.contains(tkz.peek()))
            throw new AssignToReserved(tkz.peek(), tkz.getLine());
        String identifier = tkz.consume();
        if(tkz.peek("=")){
            tkz.consume();
        }else{
            throw new IllegalAssignment(tkz.getLine());
        }
        ExprNode expression = parseExpression();
        return new AssignmentNode(identifier,expression);
    }

    //    ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
    private PlanNode parseActionCommand() {
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
            throw  new NoCommandMatch(tkz.peek(), tkz.getLine());
        }
    }

    //    MoveCommand → move Direction
    private PlanNode parseMoveCommand() {
        Direction direction = parseDirection();
        return new MoveNode(direction);
    }
    //    RegionCommand → invest Expression | collect Expression
    private PlanNode parseRegionCommand() {
        String regionCommand = tkz.consume();
        ExprNode expression = parseExpression();
        if(regionCommand.equals("invest")){
            return new InvestNode(expression);
        }else{
            return new CollectNode(expression);
        }
    }
    //    AttackCommand → shoot Direction Expression
    private PlanNode parseAttackCommand() {
        Direction direction = parseDirection();
        ExprNode expression = parseExpression();
        return new AttackNode(direction, expression);
    }

    //    Direction → up | down | upleft | upright | downleft | downright
    private Direction parseDirection() {
        String direction = tkz.consume();
        return switch (direction) {
            case "up" -> Direction.UP;
            case "down" -> Direction.DOWN;
            case "upright" -> Direction.UPRIGHT;
            case "upleft" -> Direction.UPLEFT;
            case "downright" -> Direction.DOWNRIGHT;
            case "downleft" -> Direction.DOWNLEFT;
            default -> throw new InvalidDirection(direction, tkz.getLine());
        };
    }

    //    BlockStatement → { Statement* }
    private PlanNode parseBlockStatement() {
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
    private PlanNode parseIfStatement() {
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
    private PlanNode parseWhileStatement() {
        tkz.consume("while");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        PlanNode s = parseStatement();
        return new WhileEvaluator(expression,s);
    }

    //    Expression → Expression + Term | Expression - Term | Term // T ((+T) | (-T))*
    private ExprNode parseExpression() {
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
    private ExprNode parseTerm() {
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
    private ExprNode parseFactor() {
        ExprNode v = parsePower();
        while (tkz.peek("^")) {
            tkz.consume();
            v = new ArithmeticEvaluator(v, "^", parseFactor());
        }
        return v;
    }

    //    Power → <number> | <identifier> | ( Expression ) | InfoExpression
    private ExprNode parsePower() {
        if (isNumber(tkz.peek())) {
            return new IntLit(Integer.parseInt(tkz.consume()));
        }else if(isString(tkz.peek())){
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
    private ExprNode parseInfoExpression() {
        if(tkz.peek("opponent")){
            tkz.consume();
            return new OpponentNode();
        }else{
            tkz.consume("nearby");
            Direction direction = parseDirection();
            return new NearByNode(direction);
        }
    }

    private static boolean isNumber(String str){
        return str.matches("\\d+");
    }

    private static boolean isString(String str){
        return str.matches("[a-zA-Z]+");
    }

//    public static Predicate<String> isNumber = str -> str.matches("\\d+");
//    public static Predicate<String> isString = str -> str.matches("[a-zA-Z]+");

//    private boolean elem(String x, List<String> list){
//        Predicate<String> equalsX = a -> a.equals(x);
//        return list.stream().anyMatch(equalsX);
//    }
}