import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Scanner;

/**
 * Created by yu on 2016/9/29.
 *
 */
public class ConsoleTrain {
    @Test
    public  void  dealWithInandOutput(){
        GraphyUtil graphyUtil = new GraphyUtil();
        ISolutions solutions = new Solutions();
        //接收输入
        Scanner scanner=new Scanner(System.in);
        String inPutInfo = scanner.nextLine();
        JSONObject graphyInfo = graphyUtil.dealWithString(inPutInfo);
        //1-5题输出
        OutputFormat(1, solutions.routeDistance(graphyInfo, "A", "B", "C"));
        OutputFormat(2, solutions.routeDistance(graphyInfo, "A", "D"));
        OutputFormat(3, solutions.routeDistance(graphyInfo, "A", "D", "C"));
        OutputFormat(4, solutions.routeDistance(graphyInfo, "A", "E" ,"B", "C", "D"));
        OutputFormat(5, solutions.routeDistance(graphyInfo, "A", "E", "D"));

        //6-7题输出
        OutputFormat(6, solutions.searchWay(graphyInfo,"C","C"));
        OutputFormat(7, solutions.searchWay(graphyInfo,"A","C",1));

        //8-9输出
        OutputFormat(8, solutions.getShortRouteAC(graphyInfo, "A", "C"));
        OutputFormat(9, solutions.getShortRouteForRecycle(graphyInfo, "B"));
        //10题输出
        OutputFormat(10, solutions.countRouteNumer( graphyInfo,30, "C","C"));


    }

    /**
     * 输出格式控制
     * @param i
     * @param num
     */
    public void OutputFormat(int i,int num){
        if(num==-1){
            System.out.println("Output#"+i+":"+"NO SUCH ROUTE");
        }else {
            System.out.println("Output#"+i+":"+num);
        }
    }

}
