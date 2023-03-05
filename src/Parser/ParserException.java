package Parser;

public class ParserException extends RuntimeException{
    protected ParserException(String msg){
        super(msg);
    }

    public static class TokenRequired extends ParserException{
        public TokenRequired() {
            super("Not have Token to proceed the parser");
        }
    }

    public static class InvalidDirection extends ParserException{
        public InvalidDirection(String direction, int line) {
            super("Direction '" + direction + "' is invalid at line " + line);
        }
    }

    public static class AssignToReserved extends ParserException{
        protected AssignToReserved(String reserved,int line) {
            super("There assignment to reserved word '" + reserved +"' at line " + line);
        }
    }

    public static class IllegalAssignment extends ParserException{
        protected IllegalAssignment(int line) {
            super("Illegal to assign variable at line " + line);
        }
    }

    public static class LeftoverToken extends ParserException{
        public LeftoverToken(int line) {
            super("Leftover Token at line " + line);
        }
    }

    public static class NoCommandMatch extends ParserException{
        public NoCommandMatch(String wrongCmd,int line) {
            super("Command '" + wrongCmd + "' is not match in 'Action Command'. At line " + line);
        }
    }

    public static class NoConfigMatch extends ParserException{
        public NoConfigMatch(String wrongConfig,int line) {
            super("Config '" + wrongConfig + "' is not match in 'Identifier Config'. At line " + line);
        }
    }
}
