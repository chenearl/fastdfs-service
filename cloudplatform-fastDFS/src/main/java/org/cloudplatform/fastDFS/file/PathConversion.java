/**
 * Project Name:cloudplatform-fastDFS
 * File Name:PathConversion.java
 * Package Name:org.cloudplatform.fastDFS.file
 * Date:2018年7月11日下午4:42:17
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package org.cloudplatform.fastDFS.file;

import org.cloudplatform.fastDFS.model.FastdfsPath;

import com.github.tobato.fastdfs.domain.StorePath;

/**
 * 
 * ClassName: PathConversion
 * (存储路径转换).
 * date: 2018年7月11日 下午4:42:20
 * @author tanchao
 * @version
 */
public class PathConversion {
	
    public static String imageServerUrl;

	public static FastdfsPath StorePathConversion(StorePath storePath) {
		FastdfsPath fastdfsPath = new FastdfsPath();
		fastdfsPath.setAbsolutePath(imageServerUrl+"/"+storePath.getGroup()+"/"+storePath.getPath());
		fastdfsPath.setRelativePath("/"+storePath.getGroup()+"/"+storePath.getPath());
		fastdfsPath.setStoreGroup(storePath.getGroup());
		fastdfsPath.setStorePath(storePath.getPath());
		return fastdfsPath;
	}
}

