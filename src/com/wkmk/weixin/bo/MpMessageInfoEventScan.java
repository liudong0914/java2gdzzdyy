package com.wkmk.weixin.bo;

/**
 * 二维码扫描
 */
public class MpMessageInfoEventScan extends MpMessageInfoEvent {   
	private String ScanType;//qrcode
	private String ScanResult;//扫描返回的结果
	
	public String getScanType() {
		return ScanType;
	}
	public void setScanType(String scanType) {
		ScanType = scanType;
	}
	public String getScanResult() {
		return ScanResult;
	}
	public void setScanResult(String scanResult) {
		ScanResult = scanResult;
	}
}