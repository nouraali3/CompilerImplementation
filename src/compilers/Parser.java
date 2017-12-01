
package compilers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Parser 
{
        static File inputFile=new File("scanner_output.txt");
        
        static FileWriter outputFile = null; 
        static Scanner in=null;
        static String tokenValue = new String();
        static String tokenType = new String();
        
    public static void main (String [] args)
    {
        Token token=new Token();
        try 
        {
            outputFile = new FileWriter("parser_output.txt");
            in=new Scanner(inputFile);
        } 
        catch (IOException ex) 
            {System.err.print("error in creating output file or in reading input file");}

        tokenValue =in.next();
        in.next();
        tokenType=in.next();
        token.setParameters(tokenValue, tokenType);
        System.out.println(tokenValue+" "+tokenType);
        program(token);
    }
    
    private static void program(Token t)
    {
        stmt_seq(t);
        try
            {outputFile.write("program found");}
        catch (IOException ex) 
            {System.err.println("error in program function");}
    }

    private static void stmt_seq(Token t) 
    {
        stmt(t);
        while(t.getTokenValue().equals(";"))
        {
            match(t);
            stmt(t);
        }
        try
            { outputFile.write("stmt_seq found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in stmt_seq function");}
    }
    
    private static void stmt(Token t)
    {
        switch (t.getTokenValue())
        {
            case "if":
                if_stmt(t);
                break;
            case "repeat":
                repeat_stmt(t);
                break;
            case "read":
                read_stmt(t);
                break;
            case "write":
                write_stmt(t);
                break;
            default:
                check_token_type(t);
                break;
            
        }
        try
            {outputFile.write("stmt found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in stmt function");}
    }
    
    private static void if_stmt(Token t)
    {
        match(t);
        exp(t);
        match(t);
        stmt_seq(t);
        if(t.getTokenValue().equals("else"))
        {
            match(t);
            stmt_seq(t);
        }
        match(t);
        try
            {outputFile.write("if_stmt found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in if_stmt function");}
    }
    
    private static void repeat_stmt (Token t)
    {
        match(t);
        stmt_seq(t);
        match(t);
        exp(t);
        try
            {outputFile.write("repeat_stmt found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in repeat_stmt function");}
    }

    
    private static void check_token_type(Token t)
    {
        if (t.getTokenType().equals("identifier"))
        {
            assign_stmt(t);
        }
    }

    private static void assign_stmt(Token t)
    {
        match(t);
        match(t);
        exp(t);
        try 
            {outputFile.write("assign_stmt found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in assign_stmt function");}
    }
    
    private static void read_stmt(Token t)
    {
        match(t);
        match(t);
        try
            {outputFile.write("read_stmt found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in read_stmt function");}
    }

    private static void write_stmt(Token t)
    {
        match(t);
        exp(t);
        try
            {outputFile.write("write_stmt found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in write_stmt function");}
    }

    private static void exp(Token t)
    {
        simple_exp(t);
        if(t.tokenValue.equals("<") || t.getTokenValue().equals("="))
        {
            comp_op(t);
            simple_exp(t);
        }
        try
            {outputFile.write("exp found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in exp function");}
    }

    private static void comp_op(Token t)
    {
        switch(t.getTokenValue())
        {
            case "<":
                match(t);
                break;
            case "=":
                match(t);
                break;
        }
        try
            {outputFile.write("comp_op found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in comp_op function");}
    }
    
    private static void simple_exp(Token t)
    {
        term(t);
        while(t.getTokenValue().equals("+") || t.getTokenValue().equals("-"))
        {
            addop(t);
            term(t);
        }
        try
            {outputFile.write("simple_op found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in simple_op function");}
    }
    
    private static void addop(Token t)
    {
        switch(t.getTokenValue())
        {
            case "+":
                match(t);
                break;
            case "-":
                match(t);
                break;
        }
        try
            {outputFile.write("addop found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in addop function");}
    }
    
    private static void term(Token t)
    {
        factor(t);
        while(t.getTokenValue().equals("*") || t.getTokenValue().equals("/"))
        {
            mulop(t);
            factor(t);
        }
        try
            {outputFile.write("term found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in term function");}
    }
    
    private static void mulop(Token t)
    {
        switch(t.getTokenValue())
        {
            case "*":
                match(t);
                break;
            case "/":
                match(t);
                break;
        }
        try
            {outputFile.write("mulop found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in mulop function");}
    }
    
    private static void factor(Token t)
    {
        switch(t.getTokenType())
        {
            case "number":
                match(t);
                break;
            case "identifier":
                match(t);
                break;
            default:
                check_token_value(t);
                break;
        }
        try
            {outputFile.write("factor found"+"\r\n");}
        catch (IOException ex) 
            {System.err.println("error in factor function");}
    }
    
    private static void check_token_value(Token t)
    {
        if(t.getTokenValue().equals("("))
        {
            match(t);
            exp(t);
            match(t);
        }
    }
    
    private static void match(Token t)
    {
       if(in.hasNext())
       {
            tokenValue=in.next();
            in.next();
            tokenType=in.next();
            System.out.println(tokenValue+" "+tokenType);
            t.setParameters(tokenValue, tokenType);
       }    
    }
}
