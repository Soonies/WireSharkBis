package Filtre;

import java.util.Stack;

public class InfixToPostfix {

    private static int precedence(char c){
        switch (c){
            case '|':
                return 1;
            case '&':
                return 2;
        }
        return -1;
    }

    public static String f(String expression){

        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <expression.length() ; i++) {
            char c = expression.charAt(i);

            //check if char is operator
            if(precedence(c)>0){
                while(stack.isEmpty()==false && precedence(stack.peek())>=precedence(c)){
                    result += stack.pop();
                }

                stack.push(' ');
                stack.push(c);
                stack.push(' ');
            }else if(c==')'){
                char x = stack.pop();
                while(x!='('){
                    result += x;
                    x = stack.pop();
                }
            }else if(c=='('){
                stack.push(c);
            }else{
                //character is neither operator nor ( 
                result += c;
            }
        }
        for (int i = 0; i < stack.size() ; i++) {
            result += stack.pop();
        }
        return result;
    }

    
    public static void main(String[] args) {
        String exp = "tcp.portSrc=80";
        System.out.println("Infix Expression: " + exp);
        System.out.println("Postfix Expression: " + f(exp));
    }
}