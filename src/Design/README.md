# _Design Problems_


## _Browser History Creation:_

请设计一个数据结构来模拟浏览器的历史记录功能。

浏览器允许用户访问新网页、返回上一页、以及前进到之前访问过的页面。

你需要实现以下功能：

### Initialize
创建浏览器历史记录对象时，需要提供一个 首页地址 homepage。

浏览器一开始停留在该页面。

---
### Operations
1. `visit(url)`

+ 访问一个新的网页 `url`。

+ 当前页面会被记录到历史中

+ 浏览器跳转到新页面

2. `back(steps)`

返回历史记录中的页面。

+ 最多返回 `steps` 步

+ 如果历史不足，则返回到最早的页面

+ 返回操作完成后，返回当前页面地址

3. `forward(steps)`

向前移动到之前返回过的页面。

+ 最多前进 steps 步

+ 如果没有足够的 `forward` 记录，则前进到最远的页面

+ 返回操作完成后，返回 当前页面地址


```java
public class BrowserHistory {
    Stack<String> toBack;
    Stack<String> toForward;

    public BrowserHistory(String homepage){
        this.toBack = new Stack<>();
        this.toForward = new Stack<>();
        toBack.push(homepage);
    }

    public void visit(String url){
        toBack.push(url);
        toForward = new Stack<>();
    }

    public String back(int steps){
        for (int i = 0; i < steps; i++) {
            toForward.push(toBack.pop());
        }
        return toBack.isEmpty()? toForward.peek() : toBack.peek();
    }

    public String forward(int steps){
        for (int i = 0; i < steps; i++) {
            toBack.push(toForward.pop());
        }
        return toBack.peek();
    }
}
```