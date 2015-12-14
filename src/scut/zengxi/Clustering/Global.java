package scut.zengxi.Clustering;

import java.text.DecimalFormat;

/**
 * Created by zengxi on 2015/12/8.
 */
public class Global {
  //  private static DecimalFormat df3  = new DecimalFormat("###.000");     //用于保留小数点后三位
    /**
     * 计算两个用户的相似度聚类：此处用欧式距离作为相似度
     * @param data1  归一化后的用户1向量
     * @param data2  归一化后的用户2向量
     * @return  用户相似度
     */
    public static double calEuraDist(double[] data1,double[] data2){
        double d = 0.0;
        for(int i=0;i<data1.length;i++){
            d+=Math.pow((data1[i] - data2[i]),2);
        }
        d=Math.sqrt(d);
        return d;
    }
}
