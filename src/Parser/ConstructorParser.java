package Parser;

import java.util.ArrayList;
import java.util.List;
import Tokenizer.Tokenizer;
import Parser.ParserException.*;
import AST.*;
import AST.Node.*;
import Game.Direction;

public class ConstructorParser implements Parser{
    private final Tokenizer tkz;
    private final List<String> commands = List.of("done", "relocate", "move", "invest", "collect", "shoot");
    private final List<String> reserved = List.of("collect", "done", "down", "downleft", "downright", "else", "if", "invest", "move", "nearby", "opponent", "relocate", "shoot", "then", "up", "upleft", "upright", "while");

    public ConstructorParser(Tokenizer tkz){
        if (!tkz.hasNextToken())
            throw new ParserException.TokenRequired();
        this.tkz = tkz;
    }
    @Override
    public ExecNode parse() {
        ExecNode plan = parseStatements();
        // reject if there is remaining token
        if (tkz.hasNextToken())
            throw new LeftoverToken(tkz.getLine());
        return plan;
    }
    private ExecNode parseStatements(){
        ArrayList<ExecNode> statements = new ArrayList<>();
        while (tkz.hasNextToken() && !tkz.peek("}")) {
            statements.add(parseStatement());
        }
        return new StatementsEvaluator(statements);
    }
    private ExecNode parseStatement(){
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
    private ExecNode parseCommand() {
        if(commands.contains(tkz.peek())){
            return parseActionCommand();
        }else{
            return parseAssignmentStatement();
        }
    }
    private ExecNode parseAssignmentStatement() {
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
    private ExecNode parseActionCommand() {
        if(tkz.peek("done")){
            tkz.consume();
            return new DoneNode();
        }else if(tkz.peek("relocate")){
            tkz.consume();
            return new RelocateNode();
        }else if(tkz.peek("move")){
            tkz.consume();
            return parseMoveCommand();
        }else if(tkz.peek("invest") || tkz.peek("collect")){
            return parseRegionCommand();
        }else if(tkz.peek("shoot")){
            tkz.consume();
            return parseShootCommand();
        }else{
            throw  new NoCommandMatch(tkz.peek(), tkz.getLine());
        }
    }
    private ExecNode parseMoveCommand() {
        Direction direction = parseDirection();
        return new MoveNode(direction);
    }
    private ExecNode parseRegionCommand() {
        String regionCommand = tkz.consume();
        ExprNode expression = parseExpression();
        if(regionCommand.equals("invest")){
            return new InvestNode(expression);
        }else{
            return new CollectNode(expression);
        }
    }
    private ExecNode parseShootCommand() {
        Direction direction = parseDirection();
        ExprNode expression = parseExpression();
        return new ShootNode(direction, expression);
    }
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
    private ExecNode parseBlockStatement() {
        tkz.consume("{");
        if(tkz.peek("}")){
            tkz.consume("}");
            return new EmptyNode();
        }
        ExecNode s = parseStatements();
        tkz.consume("}");
        return s;
    }
    private ExecNode parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        ExecNode ts = parseStatement();
        tkz.consume("else");
        ExecNode fs = parseStatement();
        return new IfElseEvaluator(expression,ts,fs);
    }
    private ExecNode parseWhileStatement() {
        tkz.consume("while");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        ExecNode s = parseStatement();
        return new WhileEvaluator(expression,s);
    }
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
    private ExprNode parseFactor() {
        ExprNode v = parsePower();
        while (tkz.peek("^")) {
            tkz.consume();
            v = new ArithmeticEvaluator(v, "^", parseFactor());
        }
        return v;
    }
    private ExprNode parsePower() {
        if(tkz.peek("nearby") || tkz.peek("opponent")){
            return parseInfoExpression();
        } else if (isNumber(tkz.peek())) {
            return new IntLit(Integer.parseInt(tkz.consume()));
        }else if(isString(tkz.peek())){
            return new Identifier(tkz.consume());
        }else if(tkz.peek("(")){
            tkz.consume("(");
            ExprNode v = parseExpression();
            tkz.consume(")");
            return v;
        }else{
            throw new RuntimeException();
        }
    }
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
}
