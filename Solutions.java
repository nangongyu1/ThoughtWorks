import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Set;

/**
 * Created by yu on 2016/9/29.
 *
 */
public class Solutions implements ISolutions {

    GraphyUtil graphyUtil =new GraphyUtil();
    //用函数重载的方式解决1-5题
    /**
     * two stops
     */
   public int routeDistance( JSONObject graphyInfo,String town1,String town2 ){
       int distance = 0;
       distance = twoTownsDistance(graphyInfo,town1,town2);
        return distance;
   }
    /**
     * three stops
     */
    public int routeDistance( JSONObject graphyInfo,String town1,String town2,String town3){
        int distance = 0;
        int distance1 = twoTownsDistance(graphyInfo,town1,town2);
        if(distance1 == -1){
            return -1;
        }
        int distance2 = twoTownsDistance(graphyInfo,town2,town3);
        if(distance2 == -1){
            return -1;
        }
        return distance1+distance2;
    }
    /**
     * five stops
     */
    public int routeDistance( JSONObject graphyInfo,String town1,String town2,String town3,String town4,String town5){
        int distance = 0;
        int distance1 = twoTownsDistance(graphyInfo,town1,town2);
        if(distance1 == -1){
            return -1;
        }
        int distance2 = twoTownsDistance(graphyInfo,town2,town3);
        if(distance2 == -1){
            return -1;
        }
        int distance3 = twoTownsDistance(graphyInfo,town3,town4);
        if(distance3 == -1){
            return -1;
        }
        int distance4 = twoTownsDistance(graphyInfo,town4,town5);
        if(distance4 == -1){
            return -1;
        }
        return distance1+distance2+distance3+distance4;

    }
//解决6-7题

    /**
     *第6题
     * @param graphyInfo
     * @param town1
     * @param town2
     * @return 路径条数
     *
     */
    public int searchWay(JSONObject graphyInfo,String town1,String town2){
        JSONArray townsRoute =  graphyInfo.getJSONArray("townsRoute");
        String towns = graphyInfo.getString("towns");
        int[][]  townsNodes = graphyUtil.createTownsMat(towns,townsRoute);
        int x = town1.charAt(0)-'A';
        int y = town2.charAt(0)-'A';

        //矩阵乘法
        //2次方:从C到C用两步时的路径条数，3次方：从C到C需要3步时的路径条数）
        int[][] multip2 = mmltiple(townsNodes,townsNodes);
        int[][] multip3 = mmltiple(multip2,townsNodes);

        return multip2[x][y]+multip3[x][y];
    }

    /**
     * 第7题，（第6题方法的重载）
     * @param graphyInfo
     * @param town1
     * @param town2
     * @param flag
     * @return
     */
    public int searchWay(JSONObject graphyInfo,String town1,String town2,int flag){
        JSONArray townsRoute =  graphyInfo.getJSONArray("townsRoute");
        String towns =  graphyInfo.getString("towns");
        int[][]  townsNodes = graphyUtil.createTownsMat(towns,townsRoute);
        int x = town1.charAt(0)-'A';
        int y = town2.charAt(0)-'A';

        //矩阵乘法
        //2次方:从C到C用两步时的路径条数，3次方：从C到C需要3步时的路径条数）
        int[][] multip2 = mmltiple(townsNodes,townsNodes);
        int[][] multip3 = mmltiple(multip2,townsNodes);
        int[][] multip4 = mmltiple(multip3,townsNodes);

        return multip4[x][y];
    }

    //8-9题 最短路径问题，并且要计算出路径距离 Dijkstra算法，但第9题要多加一步

    public int getShortRouteAC(JSONObject graphyInfo,String town1,String town2){
        Dijkstra dijkstraAlgorithm =new Dijkstra();
        JSONArray townsRoute =  graphyInfo.getJSONArray("townsRoute");
        String towns =  graphyInfo.getString("towns");
        int[][] distance = graphyUtil.startEndDishtance(towns, townsRoute);

        int start=(int) town1.charAt(0)-'A';
        int i = (int) town2.charAt(0)-'A';
        int[] shortPath =dijkstraAlgorithm.dijkstra(distance, start);

        /*for(int i = 0;i < shortPath.length;i++)
            System.out.println("从"+start+"出发到"+i+"的最短距离为："+shortPath[i]);*/
        return shortPath[i];
    }
    //第9题 找到哪些点能到B点，再找B点的该的最短路径，两段路之和 可最B-->B的最短路径
    public int getShortRouteForRecycle(JSONObject graphyInfo,String town1){
        Dijkstra dijkstraAlgorithm =new Dijkstra();
        JSONArray townsRoute =  graphyInfo.getJSONArray("townsRoute");
        String towns =  graphyInfo.getString("towns");
        int[][] distance = graphyUtil.startEndDishtance(towns, townsRoute);

        //dijkstra算法
        int start=(int) town1.charAt(0)-'A';
        int[] shortPath =dijkstraAlgorithm.dijkstra(distance, start);

        int[] pathdastance = new int[]{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000};
        int shortest = 0;

        for(int j=0;j<townsRoute.size();j++){
            int i = 0;
            int k = 0;
            JSONObject oneTrip = (JSONObject) townsRoute.get(j);
            String startTown =oneTrip.getString("start");
            String endTown =oneTrip.getString("end");
            int dis =oneTrip.getInt("dis");
            //寻找中间点
            if(town1.equals(endTown)){
                i = (int) startTown.charAt(0)-'A';
                pathdastance[k]=dis+shortPath[i];
                k++;
            }
        }
        //寻找出最短的距离
        shortest=pathdastance[pathdastance.length-1];
        for(int m=0;m<pathdastance.length;m++){
            if(shortest>pathdastance[m]){
                shortest=pathdastance[m];
            }
        }

        return shortest;
    }


    //10题 在规定距离内寻找满足条件的路径数

    /**
     * 递归解第10题 haveRoute(townsRoute,town1,town2) && distance[from][to] <= len
     * @param graphyInfo
     * @param len
     * @param town1
     * @param town2
     * @return
     */
    public int countRouteNumer(JSONObject graphyInfo,int len, String town1,String town2){
        JSONArray townsRoute =  graphyInfo.getJSONArray("townsRoute");
        String towns = graphyInfo.getString("towns");
        int[][] distance = graphyUtil.startEndDishtance(towns, townsRoute);
        if (len < 0) { return 0 ; }
        int from = (int)town1.charAt(0)-'A';
        int to = (int)town2.charAt(0)-'A';
        return  get_path_number_less_than(towns,distance,len,from,to);
    }

    public int get_path_number_less_than(String towns,int[][] distance,int len, int from,int to) {
        if (len < 0) { return 0 ; }
        boolean original =  distance[from][to]!=0&&distance[from][to] <= len;
        int sum = 0;
        if (original) sum = 1;
        for(int i =0;i<towns.length();i++ ) {
            String middleTowns = towns.substring(1,towns.length());
            int vertex = towns.charAt(i)-'A';
            sum += get_path_number_less_than(middleTowns, distance,len - distance[from][vertex], vertex, to);
        }

        return sum;
    }

    /**
     * 判断两点之是否有路
     * @param townsRoute
     * @param town1
     * @param town2
     * @return
     */
    public boolean haveRoute(JSONArray townsRoute,String town1,String town2){
        for(int i=0;i<townsRoute.size();i++){
            JSONObject oneTrip = (JSONObject) townsRoute.get(i);
            String startTown =oneTrip.getString("start");
            String endTown =oneTrip.getString("end");
            if(town1.equals(startTown)){
                if(town2.equals(endTown)){
                    return true;
                }
            }
        }
          return false;
    }
    //search the distance of two towns
     public int twoTownsDistance(JSONObject graphyInfo,String town1,String town2){
         int distance = 0;
         JSONArray townsRoute =  graphyInfo.getJSONArray("townsRoute");
         for(int i=0;i<townsRoute.size();i++){
             JSONObject oneTrip = (JSONObject) townsRoute.get(i);
             String startTown =oneTrip.getString("start");
             String endTown =oneTrip.getString("end");
             int dis =oneTrip.getInt("dis");
             if(town1.equals(startTown)){
                 if(town2.equals(endTown)){
                     distance = dis;
                 }
             }
         }
         if(distance == 0){
             return -1;
         }
         return distance;
     }


    /**
     * 矩阵乘法
     * @param a
     * @param b
     * @return
     */
    public int[][] mmltiple(int[][] a, int[][] b) {
        int [][] result = new int[a.length][b[0].length];
        for (int i = 0; i<a.length; i++) {
            for (int j = 0; j<b[0].length; j++) {
                for (int k = 0; k<a[0].length; k++) {
                    result[i][j]= result[i][j]+a[i][k]*b[k][j];
                }
            }
        }
        return result;
    }


}
