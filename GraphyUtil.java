import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuxh on 2016/9/29.
 * This class is for analysising graph.
 */

public class GraphyUtil {
    /**
     *将接收到的包含地图信息的字符串按","分隔为数组，再在数据内部取到各个结点，最后生成结点接邻接矩阵。
     */
    public JSONObject dealWithString(String graphStrig){
        JSONArray townsRoute = new JSONArray();
        Set<String> townsSet = new HashSet();
        JSONObject graphInfo = new JSONObject();

        String[] graphArray = graphStrig.split(",");
        for(int i=0;i<graphArray.length;i++){
            String startTown = graphArray[i].substring(0, 1);
            String endTown = graphArray[i].substring(1, 2);
            int distance = Integer.valueOf(graphArray[i].substring(2,3));

           JSONObject oneTrip = new JSONObject();
            oneTrip.put("start",startTown);
            oneTrip.put("end",endTown);
            oneTrip.put("dis", distance);
            townsRoute.add(oneTrip);

            townsSet.add(startTown);
            townsSet.add(endTown);
        }
        StringBuffer towns = new StringBuffer();
        for(String town:townsSet){
            towns=towns.append(town);
        }

        graphInfo.put("townsRoute",townsRoute);
        graphInfo.put("towns",towns.toString());
        return graphInfo;
    }

    /**
     *生成结点矩阵，有路则为1 （接邻矩阵）
     * @param towns
     * @param townsRoute
     * @return townsNodes
     */
    public int[][] createTownsMat(String towns,JSONArray townsRoute){

        int townsNodes[][] = new int[towns.length()][towns.length()];
        for(int j=0;j<townsRoute.size();j++){
            JSONObject oneTrip = (JSONObject) townsRoute.get(j);
            String startTown =oneTrip.getString("start");
            String endTown =oneTrip.getString("end");
            int x =(int) startTown.charAt(0)-'A';
            int y =(int) endTown.charAt(0)-'A';
            townsNodes[x][y]=1;
        }

        return townsNodes;
    }

    /**
     * 有向结点距离矩阵，值代表两点离的距离
     * @param towns
     * @param townsRoute
     * @return
     */
    public int[][] startEndDishtance(String towns,JSONArray townsRoute){
        int nodesDist[][] = new int[towns.length()][towns.length()];
        for(int j=0;j<townsRoute.size();j++){
            JSONObject oneTrip = (JSONObject) townsRoute.get(j);
            String startTown =oneTrip.getString("start");
            String endTown =oneTrip.getString("end");
            int dis =oneTrip.getInt("dis");
            int x =(int) startTown.charAt(0)-'A';
            int y =(int) endTown.charAt(0)-'A';
            nodesDist[x][y]=dis;
        }
        for(int k=0;k<towns.length();k++){
            for(int s=0;s<towns.length();s++){
                if(k!=s&&nodesDist[k][s]==0){
                    nodesDist[k][s]=10000;//表示此路不通
                }
            }
        }
        return nodesDist;
    }

}

