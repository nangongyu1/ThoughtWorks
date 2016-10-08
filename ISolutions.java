import net.sf.json.JSONObject;

/**
 * Created by yu on 2016/9/30.
 */
public interface ISolutions {
    /**
     * 1-5题的解决方法，方法重载
     * @param graphyInfo
     * @param town1
     * @param town2
     * @return
     */
    public int routeDistance( JSONObject graphyInfo,String town1,String town2 );
    public int routeDistance( JSONObject graphyInfo,String town1,String town2,String town3);
    public int routeDistance( JSONObject graphyInfo,String town1,String town2,String town3,String town4,String town5);

    /**
     * 6-7题 方法重载
     * @param graphyInfo
     * @param town1
     * @param town2
     * @return
     */
    public int searchWay(JSONObject graphyInfo,String town1,String town2);
    public int searchWay(JSONObject graphyInfo,String town1,String town2,int flag);

    /**
     *第8-9题 dijkstra算法
     * @param graphyInfo
     * @param town1
     * @param town2
     * @return
     */
    public int getShortRouteAC(JSONObject graphyInfo,String town1,String town2);
    public int getShortRouteForRecycle(JSONObject graphyInfo,String town1);
    /**
     * 10题 递归法解决
     * @param graphyInfo
     * @param len
     * @param town1
     * @param town2
     * @return
     */
    public int countRouteNumer(JSONObject graphyInfo,int len, String town1,String town2);
}
