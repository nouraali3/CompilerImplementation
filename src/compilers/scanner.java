
package compilers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import compilers.Token;


public class scanner {

    public scanner() {}

    public static void main(String[] args) throws IOException 
    {
        
        LinkedList <Token> lineTokens =new LinkedList<>() ;
        File in=new File("tiny_sample_code.txt");
        FileWriter out=new FileWriter("scanner_output.txt");
        Scanner s=new Scanner(in);
        int i=0;

        while(s.hasNext())
        {
            String line=s.nextLine();
//            System.out.println(line);
            lineTokens=ScannerC(line);
            if (i==0)
            {
                lineTokens.removeFirst();
                i++;
            }
            for (Token t : lineTokens) 
                out.write(t.getTokenValue()+" : "+t.getTokenType()+"\r\n");
            out.write("\r\n");
        }
        out.close();
    }
         
    private static LinkedList<Token> ScannerC(String input)
    {
        LinkedList<Token> tokenList=new LinkedList<>();
        int i=0;
        while(i<input.length())
        {
            int state=1;
            Token token =new Token();
            while( state==1 || state==2 || state==3 || state==4 || state==5 )
            {
                char c=input.charAt(i);
                switch(state)
                {
                    case 1:
                        if(c==' ' || c=='\n')
                            {state=1; i++; if(c=='\n') state=6;}
                        else if(c=='{')
                            {state=2; i++;}
                        else if(Character.isDigit(c))
                            {token.updateTokenValue(c); state=3; i++;}
                        else if(Character.isLetter(c))
                            {token.updateTokenValue(c); state=4; i++;}
                        else if(c==':')
                            {token.updateTokenValue(c); state=5; i++;}
                        else if (c=='1'|| c=='2' ) 
                            {state=1; i++;}
                        else
                            {token.updateTokenValue(c); state=6; i++;}
                        break;
                    case 2:
                        if(input.charAt(i)=='}')
                            state=1;
                        i++;
                        if(c=='\n') state=6;
                        break;
                    case 3:
                        if(Character.isDigit(c))
                            {token.updateTokenValue(c); state=3; i++;}
                        else 
                            {state=6;}
                        break;
                    case 4:
                        if(Character.isLetter(c))
                            {token.updateTokenValue(c); state=4; i++;}
                        else 
                            {state=6;}
                        break;
                    case 5:
                        if(c=='=')
                            {token.updateTokenValue(c); state=6; i++;}
                        else 
                            {state=6;}
                        break;
                }
                if(i==input.length()) state=6;
            }
            if (state == 6 )
                {
                    String t=token.getTokenValue();
                    if(isReservedWord(t))
                        token.setTokenType("reservedWord");
                    else if(isNumber(t))
                        token.setTokenType("number");
                    else if(isSpecialSymbol(t))
                        token.setTokenType("specialSymbol");     
                    else if(!t.equals(""))
                        token.setTokenType("identifier");
                    if(!(t.equals("\n")) && !(t.equals("")) )
                        tokenList.add(token);
                    
                }
        }
       
//        for (Token t : tokenList) 
//                System.out.println(t.getTokenValue()+":"+t.getTokenType()+"\n");
         return tokenList;
    }
    
    private static boolean isReservedWord(String s)
    {
        return(s.equals("if") || s.equals("then") || s.equals("else") || s.equals("end") || s.equals("repeat") || s.equals("until") || s.equals("read") || s.equals("write") );
    }
    private static boolean isNumber(String s)
    {
        if(s.length()>0)
            return(Character.isDigit(s.charAt(0)) );
        return false;
    }
    private static boolean isSpecialSymbol(String s)
    {
        return(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("=") || s.equals("<") || s.equals("(") || s.equals(")") || s.equals(";") ||s.equals(":=") );
 
    }
}


