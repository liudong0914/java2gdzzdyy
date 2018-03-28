package com.wkmk.weixin.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.util.date.DateTime;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.image.ImageUtil;
import com.util.string.Encode;
import com.wkmk.util.file.ConvertFile;
import com.wkmk.weixin.mp.MpUtil;
import com.wkmk.zx.bo.ZxHelpFile;
import com.wkmk.zx.service.ZxHelpFileManager;

/**
 * @Description: 微信上传图片和音频
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 */
public class UploadFileUtil {

	/**
	 * 保存答疑附件
	 */
	public static void saveZxHelpFile(ZxHelpFileManager manager, String type, Integer objid, String realpath, String savepath, String serverImageIds, String serverAudioIds, String audioTimes){
		try {
			String rootpath = realpath + "/upload/";
			//检查文件夹是否存在,如果不存在,新建一个
	        if (!FileUtil.isExistDir(rootpath + savepath)) {
	        	FileUtil.creatDir(rootpath + savepath);
	        }
	        
			if(!"".equals(serverAudioIds)){
				String[] audioTime = audioTimes.split(",");
		        String[] serverId = serverAudioIds.split(",");
		        
		        ZxHelpFile file = new ZxHelpFile();
				file.setFiletype("2");
				file.setConvertstatus("1");
				file.setNotifystatus("1");
				file.setCreatedate(DateTime.getDate());
				file.setType(type);
				if("1".equals(type)){
					file.setQuestionid(objid);
				}else {
					file.setAnswerid(objid);
				}
				
				String filename = null;
				String filepath = null;
				long filesize = 0;
				for(int i=0, size=serverId.length; i<size; i++){
					if(serverId[i] != null && !"".equals(serverId[i])){
						filename = MD5.getEncryptPwd(serverId[i]) + ".amr";//默认微信音频文件，但html5无法播放，需要ffmpeg转成mp3
						filepath = savepath + "/" + filename;
						filesize = MpUtil.downloadMedia(serverId[i], rootpath, filepath);
						if(filesize > 0){
							file.setFilepath(filepath);
							file.setFilesize(filesize);
							file.setTimelength(Integer.valueOf(audioTime[i]));
							manager.addZxHelpFile(file);
							
							//音频转码
							ConvertFile.convertFile(file, "add");
							manager.updateZxHelpFile(file);
						}
					}
				}
			}
			
			if(!"".equals(serverImageIds)){
		        String[] serverId = serverImageIds.split(",");
		        
				ZxHelpFile file = new ZxHelpFile();
				file.setFiletype("1");
				file.setConvertstatus("1");
				file.setNotifystatus("1");
				file.setCreatedate(DateTime.getDate());
				file.setType(type);
				if("1".equals(type)){
					file.setQuestionid(objid);
				}else {
					file.setAnswerid(objid);
				}
				
				String filename = null;
				String filepath = null;
				long filesize = 0;
				for(int i=0, size=serverId.length; i<size; i++){
					if(serverId[i] != null && !"".equals(serverId[i])){
						filename = MD5.getEncryptPwd(serverId[i]) + ".jpg";
						filepath = savepath + "/" + filename;
						filesize = MpUtil.downloadMedia(serverId[i], rootpath, filepath);
						if(filesize > 0){
							//生成图片缩略图
							ImageUtil.generateThumbnailsSubImage(rootpath + filepath, rootpath + savepath + "/135x135_" + filename, 135, 135, false);
							file.setFilepath(filepath);
							file.setFilesize(filesize);
							file.setThumbpath(savepath + "/135x135_" + filename);
							manager.addZxHelpFile(file);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
