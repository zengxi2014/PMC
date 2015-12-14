package scut.zengxi.Clustering;

import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zengxi on 2015/12/7.
 */
public class KMeans {
    private List<Session> transRecords= new LinkedList<Session>();   //存储数据库数据
    private DecimalFormat df3  = new DecimalFormat("###.000");     //用于保留小数点后三位
    private Set userSet;
    private ArrayList<DataObject> bestObjects = new ArrayList<>();
    private double bestSatisfy=Double.MAX_VALUE;
    private int k;  //指定划分的簇数
    private double mu=0.0000000000000000000001;  //迭代终止条件，当各个新质心相对于老质心偏移量小于mu时终止迭代
    private double [][]centers;  //保存上一次各簇质心的位置
    private int len; //数据的维度

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }
    //    public KMeans(){}

    /*public KMeans(int k,int len){
        super();
        this.k=k;
        centers= new double [k][];
        for(int i=0;i<k;i++)
            centers[i]=new double[len];
    }*/

    /**
     * 初始化方法
     */
    public void init(){
        centers= new double [k][];
        for(int i=0;i<k;i++)
            centers[i]=new double[len];
    }

    /**
     * 根据appId加载数据
     * @param appId
     */
    private void loadData(String appId,int numK){
        ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();
        //获取数据库的数据
        transRecords = activityRouteInfoBasicService.getSessions(appId);

        ArrayList<DataPoint> dpoints = new ArrayList<DataPoint>();
        //将数据库的数据转换为DataPoint格式
        for(int i=0;i<transRecords.size();i++){
            dpoints.add(new DataPoint(transRecords.get(i),i+"",false));
        }

        DBScan dbScan=new DBScan();
        //执行第一层聚类，相似度阈值为0.6，类簇最小数目为3
        List<Cluster> clusterList=dbScan.doDbscanAnalysis(dpoints, 0.6, 3);
        //转化为用用户id字符串组成的类簇列表
        List<List<String>> stringList=dbScan.cluster2StringList(clusterList);
        //dbScan.displayCluster(stringList);
        //获取所有的用户id
        List<String> userList=getUserSet(stringList);
        //计算出用于第二层聚类的输入数据
        double [][]dataMatrix = getDataMatrix(userList,stringList);
    /*    List<String> userList = new ArrayList<>();
        userList.add("1");
        userList.add("2");
        userList.add("3");
        userList.add("4");
        userList.add("5");
        userList.add("6");
        userList.add("7");
        userList.add("8");
        userList.add("9");
        double [][]dataMatrix = {{11,11},{1,1},{1,2},{111,110},{2,1},{2,2},{11,12},{110,110},{12,12}};
*/
        ArrayList<DataObject> dataObjects = genDataObjects(userList,dataMatrix);
        int len =0;
        if(dataObjects!=null) len = dataObjects.get(0).getVector().length;
        /*for(int i=0;i<dataMatrix.length;i++){
            for(int j=0;j<dataMatrix[i].length;j++){
                System.out.print(dataMatrix[i][j] + " ");
            }
            System.out.println();
        }*/
        for(int i=0;i<dataObjects.size();i++){
            for(int j=0;j<len;j++){
                System.out.print(dataObjects.get(i).getVector()[j] + " ");
            }
            System.out.println();
        }
        KMeans kMeans = new KMeans();
        kMeans.setK(numK);
        kMeans.setLen(len);
        kMeans.init();
        //如果设置的簇类数过大，则根据用户数的一半设置（其实还是过大了）
        if(kMeans.getK()>userList.size()/2)kMeans.setK(userList.size()/2);
        //运行kmeans聚类
        kMeans.run(dataObjects, len);
//        System.out.println("----------------------------"+getSatisfy(dataObjects));
        kMeans.printResult(dataObjects, kMeans.getK());
    }

    /**
     * 获取出现在DBScan结果中的所有用户集合
     * @param stringList   dbscan的结果
     * @return
     */
    private List getUserSet(List<List<String>> stringList){
        userSet=new HashSet<String>();
        for(List<String> list :stringList){
            for(String userId: list){
                userSet.add(userId);
            }
        }
        List<String> userList= new ArrayList<>();
        System.out.println("================");
        //将set中的用户放入List中，因为list是有序的
        for(Object userId:userSet){
            userList.add(userId.toString());
        }
        for(String userId:userList){
            System.out.println(userId);
        }
        return userList;
    }

    /**
     * 将数据对象随机分配到k个簇中并初始化k个质心，每个质心是len维的向量
     * @param objects
     */
    public void initCenter(ArrayList<DataObject> objects,int len){
        Random random = new Random(System.currentTimeMillis());
        int []count=new int[k];  //记录每个簇中有多少个元素
        int num =0;
        Iterator<DataObject> iter = objects.iterator();
        while(iter.hasNext()){
            DataObject object = iter.next();
           // int id= random.nextInt(10000)%k;      //随机将数据归入k个簇
            int id = num++%k;
            object.setCid(id);
            count[id]++;
            for(int i=0;i<len;i++){
                centers[id][i]+=object.getVector()[i];
            }
        }
        for(int i=0;i<k;i++){
            for(int j=0;j<len;j++){
                centers[i][j]/=count[i];    //通过求平均值得到随机的初始簇类中心
            }
        }
    }

    /**
     * 为每个数据对象重新划分到离它最近的那个质心
     * @param objects
     */
    public void classify(ArrayList<DataObject> objects){
        Iterator<DataObject> iter= objects.iterator();
        while(iter.hasNext()){
            DataObject object=iter.next();
            double [] vector = object.getVector();
            int index =0;
            double neardist = Double.MAX_VALUE;
            for(int i=0;i<k;i++){
                double dist = Global.calEuraDist(vector,centers[i]);
                if(dist<neardist){
                    neardist = dist;
                    index = i;
                }
            }
            object.setCid(index);
        }
    }

    /**
     * 重新计算每个簇的质心，并判断终止条件是否满足，如果不满足更新各簇的质心,如果满足就返回true
     * @param objects  数据集
     * @return
     */
    public boolean calNewCenter(ArrayList<DataObject> objects,int len){
        Random random = new Random(System.currentTimeMillis());
        boolean flag=true;
        int [] count = new int[k];
        double [][]sum= new double[k][];
        for(int i=0;i<k;i++){
            sum[i] = new double[len];
        }
        Iterator<DataObject> iter= objects.iterator();
        while(iter.hasNext()){
            DataObject object = iter.next();
            int id = object.getCid();
            count[id]++;
            for(int i=0;i<len;i++){
                sum[id][i]+=object.getVector()[i];
            }
        }
        for(int i=0;i<k;i++){
            if(count[i]!=0){
                for(int j=0;j<len;j++){
                    sum[i][j]/=count[i];
                }
            }
            // 簇中不包含任何点,及时调整质心
          /*  else{
                System.out.println("----------equal zero--------------");
                int a=(i+random.nextInt(10000))%k;
                int b=(i+random.nextInt(10000))%k;
                int c = (i+random.nextInt(10000))%k;
                for(int j=0;j<len;j++){
                    centers[i][j]=(centers[a][j]+centers[b][j]+centers[c][j])/3;
                }
            }*/
        }

        for(int i=0;i<k;i++){
            // 只要有一个质心需要移动的距离超过了mu，就返回false
            if(Global.calEuraDist(sum[i],centers[i])>=mu){
                flag=false;
                break;
            }

        }

        if(!flag){
            for(int i=0;i<k;i++){
                for(int j=0;j<len;j++){
                    centers[i][j]=sum[i][j];
                }
            }
        }

        return flag;
    }

    public double getSatisfy(ArrayList<DataObject> objects){
        int len =0;
        if(objects!=null)
            len= objects.get(0).getVector().length;
        double satisfy = 0.0;
        int []count = new int[k];
        double [] ss = new double[k];
        Iterator<DataObject> iter = objects.iterator();
        while(iter.hasNext()){
            DataObject object = iter.next();
            int id = object.getCid();
            count[id]++;
            for(int i=0;i<len;i++){
                ss[id]+=Math.pow(object.getVector()[i]-centers[id][i],2.0);
            }
        }
        for(int i=0;i<k;i++){
            satisfy+=count[i]*ss[i];
        }
       // System.out.println("satisfy="+satisfy);
        return satisfy;
    }

    public void run(ArrayList<DataObject> objects,int len){
            initCenter(objects,len);
            classify(objects);
            int iterNum=1;
            while(!calNewCenter(objects,len)){
                iterNum++;
                System.out.println("=======current iternum:"+iterNum+"============");
                classify(objects);
                if(iterNum>1000)break;

            }
/*            if(getSatisfy(objects)<bestSatisfy){
                bestObjects=null;
                bestObjects=copyDataObject(objects);
                bestSatisfy=getSatisfy(objects);
            }*/
            System.out.println("errors:"+getSatisfy(objects));
            System.out.println("iterator number:"+iterNum);
            System.out.println("*************************************************");


      //  System.out.println("--------bestSatisfy="+bestSatisfy+":"+getSatisfy(bestObjects)+"--------------");
        //打印结果
    }

    public ArrayList<DataObject> copyDataObject(ArrayList<DataObject> objects){
        ArrayList<DataObject> res = new ArrayList<>();
        for(DataObject object: objects){
            DataObject newObject = new DataObject();
            newObject.setUserId(object.getUserId());
            newObject.setVector(object.getVector());
            newObject.setCid(object.getCid());
            res.add(newObject);
        }
        return res;
    }

    public void printResult(ArrayList<DataObject> objects,int k){
        List<UserCluster> userClusters = new ArrayList<>();
        for(int i=0;i<k;i++){
            UserCluster userCluster = new UserCluster();
            userCluster.setName(i+"");
            userClusters.add(userCluster);
        }
        for(DataObject object:objects){
            userClusters.get(object.getCid()).add(object.getUserId());
        }
        for(int i=0;i<k;i++){
            if(!userClusters.get(i).isEmpty())userClusters.get(i).printCluster();
        }
    }
    /**
     * 获取第二层聚类的数据点：每个用户行为在各行为类簇中的比例
     * @param userList
     * @param stringList
     * @return
     */
    private double[][] getDataMatrix(List<String> userList,List<List<String>> stringList){
        double [][]dataMatrix = new double[userSet.size()][stringList.size()];
        for(int i=0;i<userList.size();i++){
            for(int j=0;j<stringList.size();j++){
                int count=0;
                for(int k=0;k<stringList.get(j).size();k++){
                    if(userList.get(i).equals(stringList.get(j).get(k)))count++;
                }
                dataMatrix[i][j]=count;
            }
        }

        for(int i=0;i<dataMatrix.length;i++){
            double sum=0;
            for(int j=0;j<dataMatrix[i].length;j++){
                sum+=dataMatrix[i][j];
            }
            for(int j=0;j<dataMatrix[i].length;j++){
                dataMatrix[i][j]=Double.parseDouble(df3.format(dataMatrix[i][j]*1.0/sum));
            }
        }
        return dataMatrix;
    }

    /**
     * 生成DataObject对象
     * @param list  用户ID列表
     * @param dataMatrix   用户向量
     * @return
     */
    private ArrayList<DataObject> genDataObjects(List<String> list, double[][] dataMatrix){
        ArrayList<DataObject> dataObjects = new ArrayList<DataObject>();
        for(int i=0;i<list.size();i++){
            DataObject dataObject = new DataObject();
            dataObject.setUserId(list.get(i).toString());
            dataObject.setVector(dataMatrix[i]);
            dataObjects.add(dataObject);
        }
        return dataObjects;
    }



    @Test
    public void testLoadData(){
        loadData("85d4a553-ee8d-4136-80ab-2469adcae44d",4);
    }

}
