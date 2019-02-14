/**
 * Project Name:cloudplatform-fastDFS
 * File Name:path.java
 * Package Name:org.cloudplatform.fastDFS.model
 * Date:2018年7月11日下午4:34:01
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package org.cloudplatform.fastDFS.model;

/**
 * 
 * ClassName: FastdfsPath
 * (返回路径实体).
 * date: 2018年7月11日 下午4:34:44
 * @author tanchao
 * @version
 */
public class FastdfsPath {

	/**
	 * 相对路径
	 */
	private String relativePath;
	
	/**
	 * 绝对路径
	 */
	private String absolutePath;
	
	/**
	 * 存储组名
	 */
	private String StoreGroup;
	
	/**
	 * 存储路径
	 */
	private String StorePath;
	
	/**
	 * 缩略图or封面
	 */
	private String thumbnailPath;

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getStoreGroup() {
		return StoreGroup;
	}

	public void setStoreGroup(String storeGroup) {
		StoreGroup = storeGroup;
	}

	public String getStorePath() {
		return StorePath;
	}

	public void setStorePath(String storePath) {
		StorePath = storePath;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public FastdfsPath() {


	}

	@Override
	public String toString() {
		return "FastdfsPath [relativePath=" + relativePath + ", absolutePath=" + absolutePath + ", StoreGroup="
				+ StoreGroup + ", StorePath=" + StorePath + ", thumbnailPath=" + thumbnailPath + "]";
	}

	public FastdfsPath(String relativePath, String absolutePath, String storeGroup, String storePath,
			String thumbnailPath) {
		super();
		this.relativePath = relativePath;
		this.absolutePath = absolutePath;
		StoreGroup = storeGroup;
		StorePath = storePath;
		this.thumbnailPath = thumbnailPath;
	}

}

