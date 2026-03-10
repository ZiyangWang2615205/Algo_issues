package Design;

import java.util.Stack;

public class BrowserHistory {
    Stack<String> toBack;
    Stack<String> toForward;

    public BrowserHistory(String homepage){
        this.toBack = new Stack<>();
        this.toForward = new Stack<>();
        toBack.push(homepage);
    }

    public void visit(String web){
        if(web.isEmpty()) return;
        toBack.push(web);
    }

    public String back(int steps){
        if(steps > toBack.size()) return "you don't have such more history!";
        for (int i = 0; i < steps; i++) {
            toForward.push(toBack.pop());
        }
        return toBack.isEmpty()? toForward.peek():toBack.peek();
    }

    public String forward(int steps){
        if(steps > toForward.size()) return "you don't have such more history!";
        for (int i = 0; i < steps; i++) {
            toBack.push(toForward.pop());
        }
        return toBack.peek();
    }

    public void clearHistory(){
        toBack = new Stack<>();
        toForward = new Stack<>();
    }

    public static void main(String[] args) {
        BrowserHistory bh = new BrowserHistory("google.com");
        bh.visit("uob.com");
        bh.visit("blackboard.com");
        bh.visit("timmy.com");
        System.out.println(bh.back(2));//uob
        System.out.println(bh.forward(1));//blackboard
        bh.visit("tom.com");
        System.out.println(bh.back(1));//bla
        System.out.println(bh.forward(1));//tom
        System.out.println(bh.back(5));
        System.out.println(bh.forward(1));//timmy
        bh.clearHistory();
        System.out.println(bh.back(1));
    }
}
