/**
 * Project Name:cloudplatform-webApi
 * File Name:TestFastDfs.java
 * Package Name:org.cloudplatform
 * Date:2018年7月6日下午5:46:28
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package org.cloudplatform.fastDFS.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.cloudplatform.fastDFS.model.FastdfsPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * 
 * ClassName: FastDFSClientWrapper
 * (FastDFS文件上传下载包装类).
 * date: 2018年7月6日 下午5:58:30
 * @author tanchao
 * @version
 */

@Component
public class FastDFSClientWrapper {

    private final Logger logger = LoggerFactory.getLogger(FastDFSClientWrapper.class);
    
    /** 支持的图片类型 */
    private static final String[] SUPPORT_IMAGE_TYPE = { "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP" };
    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(SUPPORT_IMAGE_TYPE);
    /** 支持的视频文件类型 */
    private static final String[] SUPPORT_VIDEO_TYPE = { "AVI", "FLV", "MP4", "WMV", "WEBM" };
    private static final List<String> SUPPORT_VIDEO_LIST = Arrays.asList(SUPPORT_VIDEO_TYPE);
    /** Office文档支持Wordd/Excel/Ppt */
    private static final String[] SUPPORT_OFFICE_TYPE = { "XLS","XLSX","DOC","DOCX","PPT","PPTX"};
    private static final List<String> SUPPORT_OFFICE_LIST = Arrays.asList(SUPPORT_OFFICE_TYPE);

    @Autowired
    private FastFileStorageClient storageClient;
    
    @Value("${fdfs.web-server-url}")
    private String fdfsServerUrl;
    
    @Value("${fdfs.thumbImage.height}")
    private String ThumbnailHeight;

    @Value("${fdfs.thumbImage.width}")
    private String Thumbnailwidth;
    /**
     * 
     * uploadFile:(上传文件  file).
     * date: 2018年7月10日 下午3:14:57
     * @param file
     * @return
     * @throws IOException
     */
    public FastdfsPath uploadFile(MultipartFile file) throws IOException {
    	/*System.out.println(FilenameUtils.getExtension(file.getOriginalFilename()));*/
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return PathConversion.StorePathConversion(storePath);
    }
    
    /**
     * 
     * uploadFile:(上传文件  file).
     * date: 2018年7月10日 下午3:14:57
     * @param file
     * @return
     * @throws IOException
     */
    public FastdfsPath uploadFile(File file) throws IOException {
    	FileInputStream input = new FileInputStream(file);
    	String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        StorePath storePath = storageClient.uploadFile(input,file.length(), suffix,null);
        return PathConversion.StorePathConversion(storePath);
    }
    
    
    /**
     * 
     * uploadImageAndCrtThumbImage:(上传图片保存原始图片并且生成缩略图).
     * date: 2018年7月9日 下午2:37:37
     * @param file
     * @return
     * @throws IOException
     */
    public FastdfsPath uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        FastdfsPath path = PathConversion.StorePathConversion(storePath);
        path.setThumbnailPath(getThumbImagesUrl(storePath));
        return path;
    }
    
    
    /**
     * 
     * uploadImageAndCrtThumbImage:(上传图片保存原始图片并且生成缩略图).
     * date: 2018年7月9日 下午2:37:37
     * @param file
     * @return
     * @throws IOException
     */
    public FastdfsPath uploadImageAndCrtThumbImage(File file) throws IOException {
    	FileInputStream input = new FileInputStream(file);
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(input,file.length(), FilenameUtils.getExtension(file.getName()),null);
        FastdfsPath path = PathConversion.StorePathConversion(storePath);
        path.setThumbnailPath(getThumbImagesUrl(storePath));
        return path;
    }
    
    
    /**
     * 
     * uploadThumbImage:(上传图片仅保存生成的缩略图).
     * date: 2018年7月9日 下午2:37:37
     * @param file
     * @return
     * @throws IOException
     */
    public FastdfsPath uploadThumbImage(MultipartFile file) throws IOException {
    	//上传原始图片并生成缩略图
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        //删除原始图片
        deleteFile(storePath.getFullPath());
        //组装缩略图路径
        StringBuffer sbpath = new StringBuffer();
    	sbpath.append(storePath.getPath()).insert(storePath.getPath().lastIndexOf("."), "_"+ThumbnailHeight+"x"+Thumbnailwidth);
    	storePath.setPath(sbpath.toString());
    	FastdfsPath path = PathConversion.StorePathConversion(storePath);
        path.setThumbnailPath(path.getRelativePath());
        return path;
    }

    /**
     * 
     * uploadFile:(将一段字符串生成一个文件上传).
     * date: 2018年7月10日 下午3:14:42
     * @param content
     * @param fileExtension
     * @return
     */
    public FastdfsPath uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return PathConversion.StorePathConversion(storePath);
    }

    /**
     * getThumbImagesUrl:(封装缩略图图片完整存储地址).
     * date: 2018年7月10日 下午3:13:40
     * @param storePath
     * @return
     */
    private String getThumbImagesUrl(StorePath storePath) {
    	StringBuffer path = new StringBuffer();
    	path.append(storePath.getFullPath()).insert(storePath.getFullPath().lastIndexOf("."), "_"+ThumbnailHeight+"x"+Thumbnailwidth);
    	String fileUrl = /*fdfsServerUrl+*/"/"+ path.toString();
        return fileUrl;
    }

    /**
     * getResAccessUrl:(封装图片完整URL地址).
     * date: 2018年7月10日 下午3:13:40
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
    	String fileUrl = fdfsServerUrl+"/"+ storePath.getFullPath();
        return fileUrl;
    }


    /**
     * 
     * deleteFile:(删除文件).
     * date: 2018年7月10日 下午3:13:55
     * @param fileUrl 文件访问地址
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }
    
    /**
     * 
     * downloadFile:(完整文件下载).
     * date: 2018年7月9日 下午3:22:26
     * @param group		分组名称
     * @param fileurl	文件访问地址
     * @return
     * @throws IOException
     */
    public byte[] downloadFile(String group,String fileurl) throws IOException {
    	byte[] file_b = storageClient.downloadFile(group, fileurl,new DownloadByteArray());
        return file_b;
    }
    
    
    /**
     * 
     * isSupportImage:(是否是支持的图片类型).
     * date: 2018年7月26日 下午4:22:36
     * @param fileExtName
     * @return
     */
    public boolean isSupportImage(String fileExtName) {
        return SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase());
    }
    
    /**
     * 
     * isSupportImage:(是否是支持的视频类型).
     * date: 2018年7月26日 下午4:22:36
     * @param fileExtName
     * @return
     */
    public boolean isSupportVideo(String fileExtName) {
        return SUPPORT_VIDEO_LIST.contains(fileExtName.toUpperCase());
    }
    
    /**
     * 
     * isSupportOffice:(判断是否为office文档,文档格式支持Excel/Word/Ppt).
     * date: 2018年8月30日 下午9:21:22
     * @param fileExtName
     * @return
     */
    public boolean isSupportOffice(String fileExtName) {
        return SUPPORT_OFFICE_LIST.contains(fileExtName.toUpperCase());
    }
    
}