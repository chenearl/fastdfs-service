package org.cloudplatform.fastDFS;

import org.csource.fastdfs.*;

public class TestUpload {
    public static void main(String[] args) throws Exception {
        //1、加载配置文件 物理url
        ClientGlobal.init("D:\\bw-repository\\fsshop\\src\\bw-fsshop\\bw-shopweb\\src\\main\\resources\\fdfs_client.conf");
        //2、构建一个管理者客户端
        TrackerClient client = new TrackerClient();
        //3、通过客户端来连接得到一个服务端对象
        TrackerServer trackerServer = client.getConnection();
        //4、声明构建一个存储的服务端
        StorageServer storageServer = null;
        //5、创建一个storage的client来获取存储服务器的客户端对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //6、上传文件
        /*第三个参数的文件的扩展信息，比如是当我打开图片的属性，
        里面有详情信息（声明像素、宽度、长度）等都是可以指定的
         指定为null也行
        */
        String[] strings=  storageClient.upload_file("C:\\Users\\lzq\\Desktop\\慧智\\二次修改\\英文图片\\details\\X7_en.jpg", "jpg", null);
        //        //7、显示上传结果file_id
        for (String str:strings){
            System.out.println(str);
        }

    }
    //http://www.fusionsilicon.com:8888/group1/M00/00/01/rBAAClxGtq2ABcX1AA8Pd1KZ4D8163.pdf

}
