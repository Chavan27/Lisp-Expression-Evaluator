/************************************************************************************
 *
 *  		CSC220 Programming Project#2
 *  
 * 
 *
 * Specification: 
 *
 * Taken from Project 7, Chapter 5, Page 178
 * I have modified specification and requirements of this project
 *
 * Ref: http://www.gigamonkeys.com/book/        (see chap. 10)
 *
 * In the language Lisp, each of the four basic arithmetic operators appears 
 * before an arbitrary number of operands, which are separated by spaces. 
 * The resulting expression is enclosed in parentheses. The operators behave 
 * as follows:
 *
 * (+ a b c ...) returns the sum of all the operands, and (+ a) returns a.
 *
 * (- a b c ...) returns a - b - c - ..., and (- a) returns -a. 
 *
 * (* a b c ...) returns the product of all the operands, and (* a) returns a.
 *
 * (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1/a. 
 *
 * Note: + * - / must have at least one operand
 *
 * You can form larger arithmetic expressions by combining these basic 
 * expressions using a fully parenthesized prefix notation. 
 * For example, the following is a valid Lisp expression:
 *
 * 	(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 1))
 *
 * This expression is evaluated successively as follows:
 *
 *	(+ (- 6) (* 2 3 4) (/ 3 1 -2) (+ 1))
 *	(+ -6 24 -1.5 1)
 *	17.5
 *
 * Requirements:
 *
 * - Design and implement an algorithm that uses Java API stacks to evaluate a 
 *   valid Lisp expression composed of the four basic operators and integer values. 
 * - Valid tokens in an expression are '(',')','+','-','*','/',and positive integers (>=0)
 * - Display result as floting point number with at 2 decimal places
 * - Negative number is not a valid "input" operand, e.g. (+ -2 3) 
 *   However, you may create a negative number using parentheses, e.g. (+ (-2)3)
 * - There may be any number of blank spaces, >= 0, in between tokens
 *   Thus, the following expressions are valid:
 *   	(+   (-6)3)
 *   	(/(+20 30))
 *
 * - Must use Java API Stack class in this project.
 *   Ref: http://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
 * - Must throw LispExpressionEvaluatorException to indicate errors
 * - Must not add new or modify existing data fields
 * - Must implement these methods : 
 *
 *   	public LispExpressionEvaluator()
 *   	public LispExpressionEvaluator(String inputExpression) 
 *      public void reset(String inputExpression) 
 *      public double evaluate()
 *      private void evaluateCurrentOperation()
 *
 * - You may add new private methods
 *
 *************************************************************************************/

package PJ2;

import java.util.*;




public class LispExpressionEvaluator{
    // Current input Lisp expression
    private String inputExpr;
    
    // Main expression stack, see algorithm in evaluate()
    private LinkedStack<Object> inputExprStack;
    private LinkedStack<Double> evaluationStack;
    
    
    
    // default constructor
    // set inputExpr to ""
    // create LinkedStack objects
    public LispExpressionEvaluator(){
        inputExpr="";
        inputExprStack = new LinkedStack<Object>();
        evaluationStack = new LinkedStack<Double>();
    }
    
    // constructor with an input expression
    // set inputExpr to inputExpression
    // create LinkedStack objects
    public LispExpressionEvaluator(String inputExpression){
        // add statements
        inputExpr=inputExpression;
        inputExprStack = new LinkedStack<Object>();
        evaluationStack = new LinkedStack<Double>();
    }
    
    // set inputExpr to inputExpression
    // clear stack objects
    public void reset(String inputExpression)
    {
        // add statements
        if(inputExpression == null)
        {
            throw new LispExpressionEvaluatorException();
        }
        inputExpr=inputExpression;
        inputExprStack.clear();
        evaluationStack.clear();
        
    }
    
    
    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    //          Pop operands from inputExprStack and push them onto
    //                  evaluationStack until you find an operator
    //          Apply the operator to the operands on evaluationStack
    //          Push the result into inputExprStack
    //
    
    private void evaluateCurrentOperation(){
        double result=0;
        if(inputExprStack.empty() ){
            throw new LispExpressionEvaluatorException("Error, the stack is empty");
        }
        Object oper = inputExprStack.pop();
        
        while ( oper instanceof String ) {
            double value = Double.parseDouble(String.valueOf(oper));
            
            evaluationStack.push(value);
            
            if(inputExprStack.empty() ){
                throw new LispExpressionEvaluatorException("Error1");
            }
            else{
                oper = inputExprStack.pop();
            }
        }
        
        try{
            String aToken = oper.toString() ;
            char item = aToken.charAt(0);
            switch (item) {
                case '+':
                    if(evaluationStack==null || evaluationStack.empty()){
                        throw new LispExpressionEvaluatorException("error.add");
                    }
                    while (!evaluationStack.empty() ) {
                        result += evaluationStack.pop();
                    }
                    inputExprStack.push(String.valueOf(result));
                    break;
                    
                case '-':
                    if(evaluationStack==null || evaluationStack.empty()){
                        throw new LispExpressionEvaluatorException("error.sub");
                    }
                    result = evaluationStack.pop();
                    if (evaluationStack.empty()) {
                        result = -result;
                        inputExprStack.push(String.valueOf(result));
                    }
                    else {
                        while(!evaluationStack.empty()) {
                            result -= evaluationStack.pop();
                        }
                        inputExprStack.push(String.valueOf(result));
                    }
                    break;
                    
                    
                case '*':
                    if(evaluationStack==null || evaluationStack.empty()){
                        throw new LispExpressionEvaluatorException("error.mult");
                    }
                    result = 1;
                    while ( !evaluationStack.empty() ) {
                        result *= evaluationStack.pop();
                    }
                    inputExprStack.push(String.valueOf(result));
                    
                    break;
                    
                case '/':
                    if(evaluationStack==null || evaluationStack.empty()){
                        throw new LispExpressionEvaluatorException("error.div");
                    }
                    result = evaluationStack.pop();
                    
                    if (evaluationStack.empty()) {
                        result = 1/result;
                        inputExprStack.push(String.valueOf(result));
                    }
                    else {
                        while(!evaluationStack.empty()) {
                            result /=evaluationStack.pop();
                        }
                        inputExprStack.push(String.valueOf(result));
                    }
                    break;
                    
                case '(':
                default:
                    throw new LispExpressionEvaluatorException(item + " is not a legal expression operator");
                    
            }
        }
        catch ( LispExpressionEvaluatorException e){
            throw new LispExpressionEvaluatorException( e.getMessage());
        }
    }
    
    
    
    
    /**
     * This funtion evaluates current Lisp expression in inputExpr
     * It return result of the expression
     *
     * The algorithm:
     *
     * Step 1   Scan the tokens in the string.
     * Step 2           If you see an operand, push operand object onto the inputExprStack
     * Step 3           If you see "(", next token should be an operator
     * Step 4           If you see an operator, push operator object onto the inputExprStack
     * Step 5           If you see ")"  // steps in evaluateCurrentOperation() :
     * Step 6                   Pop operands and push them onto evaluationStack
     *                                  until you find an operator
     * Step 7                   Apply the operator to the operands on evaluationStack
     * Step 8                   Push the result into inputExprStack
     * Step 9    If you run out of tokens, the value on the top of inputExprStack is
     *           is the result of the expression.
     */
    public double evaluate(){
        Scanner inputExprScanner = new Scanner(inputExpr);
        inputExprScanner = inputExprScanner.useDelimiter("\\s*");
        while (inputExprScanner.hasNext()){
            if (inputExprScanner.hasNextInt()){
                String dataString = inputExprScanner.findInLine("\\d+");
                inputExprStack.push(dataString);
            }
            else{
                try{
                    String aToken = inputExprScanner.next();
                    char item = aToken.charAt(0);
                    switch (item){
                        case '(':
                            aToken = inputExprScanner.next();
                            item = aToken.charAt(0);
                            switch (item){
                                case '+':
                                    inputExprStack.push(item);
                                    break;
                                case '-':
                                    inputExprStack.push(item);
                                    break;
                                case '*':
                                    inputExprStack.push(item);
                                    break;
                                case '/':
                                    inputExprStack.push(item);
                                    break;
                                default:
                                    throw new LispExpressionEvaluatorException(item + " is not a legal expression operator");
                            }
                            break;
                            
                        case ')':
                            evaluateCurrentOperation();
                            break;
                            
                        default:
                            throw new LispExpressionEvaluatorException(item + " is not a legal expression operator");
                    } // end switch
                }//end try
                catch ( LispExpressionEvaluatorException e){
                    throw new LispExpressionEvaluatorException( e.getMessage());
                }//end catch
            } // end else
        } // end while
        double result= Double.parseDouble(String.valueOf(inputExprStack.pop()));
        if (!inputExprStack.empty()){
            throw new LispExpressionEvaluatorException ("This stack still has values, but there is no operand");
        }
        return result;
    }
    
    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    // Quick test is defined in main()
    //=====================================================================
    
    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect)
    {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
        expr.reset(s);
        try {
            result = expr.evaluate();
            System.out.printf("Evaluated result : %.2f\n", result);
        }
        catch (LispExpressionEvaluatorException e) {
            System.out.println("Evaluated result :"+e);
        }
        
        System.out.println("-----------------------------");
    }
    
    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExpressionEvaluator expr= new LispExpressionEvaluator();
        //expr.setDebug();
        String test1 = "(+ (- 6) (* 2 3 4) (/ 3 1 -2) (+ 0))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+ 0))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 ))(* 1))";
        String test4 = "(+ (/2)(+ 1))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        String test7 = "(+ (*))";
        String test8 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ ))";
        evaluateExprTest(test1, expr, "16.50");
        evaluateExprTest(test2, expr, "-378.12");
        evaluateExprTest(test3, expr, "4.50");
        evaluateExprTest(test4, expr, "1.5");
        evaluateExprTest(test5, expr, "Infinity or LispExpressionEvaluatorException");
        evaluateExprTest(test6, expr, "LispExpressionEvaluatorException");
        evaluateExprTest(test7, expr, "LispExpressionEvaluatorException");
        evaluateExprTest(test8, expr, "LispExpressionEvaluatorException");
    }
}


class LinkedStack<T>
{
    
    // Data fields
    private Node<T> topNode; // references the first node in the chain
    private int count;       // number of data in this stack
    
    public LinkedStack()
    {
        // add stataments
        topNode = null;
        count=0;
    } // end default constructor
    
    public void push(T newData)
    {
        // add stataments
        Node<T> newNode = new Node<T>(newData, topNode);
        topNode = newNode;
        count++;
    } // end push
    
    public T peek()
    {
        // add stataments
        T top = null;
        
        if (topNode != null)
            top = topNode.getData();
        
        return top;
    } // end peek
    
    public T pop()
    {
        // add stataments
        T top = peek();
        
        if (topNode != null)
        {
            topNode = topNode.getNextNode();
            count--;
        }
        
        return top;
    } // end pop
    
    public boolean empty()
    {
        // add stataments
        return topNode == null;
    } // end empty
    
    public int size()
    {
        // add stataments
        return count;
    } // end isEmpty
    
    public void clear()
    {
        // add stataments
        topNode = null;
        count=0;
    } // end clear
    
    public String toString()
    {
        // add stataments
        // note: data class in stack must implement toString() method
        //       return a list of data in Stack, separate them with ','
        String result = "[";
        Node<T> currentNode=topNode; // references the first node in the chain
        while (currentNode != null) {
            result = result + currentNode.getData() + ",";
            currentNode = currentNode.getNextNode();
        }
        result = result + "]";
        return result;
    }
    
    
 
    
} // end Stack


class Node<T>
{
    private T       data; // entry in stack
    private Node<T> next; // link to next node
    
    Node(T aData)
    {
        this(aData, null);
    } // end constructor
    
    Node(T aData, Node<T> nextNode)
    {
        data = aData;
        next = nextNode;
    } // end constructor
    
    T getData()
    {
        return data;
    } // end getData
    
    void setData(T aData)
    {
        data = aData;
    } // end setData
    
    Node<T> getNextNode()
    {
        return next;
    } // end getNextNode
    
    void setNextNode(Node<T> nextNode)
    {
        next = nextNode;
    } // end setNextNode
} // end Node


