
package compilers;

public class Token {
    String tokenValue;
    String tokenType;

    public Token() {
        tokenType=new String();
        tokenValue=new String();
    }
    public Token(char c) {
        tokenValue+=c;
    }
    

    public String getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
    public void updateTokenValue(char c)
    {
        this.tokenValue+=c;
    }
    
    
}