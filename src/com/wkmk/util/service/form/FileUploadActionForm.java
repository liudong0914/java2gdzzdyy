package com.wkmk.util.service.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class FileUploadActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private FormFile content;//ScienceWord客户端将dsc文件上传至web平台的文档Base64流
	private FormFile htmlContent;//ScienceWord客户端将dsc文件上传至web平台的文档HTML流
	private FormFile file;
	private FormFile file1;
	private FormFile file2;
	private FormFile file3;
	private FormFile file4;
	private FormFile file5;
	private FormFile file6;
	private FormFile file7;
	private FormFile file8;
	private FormFile file9;
	private FormFile file10;
	private FormFile file11;
	private FormFile file12;
	private FormFile file13;
	private FormFile file14;
	private FormFile file15;
	private FormFile file16;
	private FormFile file17;
	private FormFile file18;
	private FormFile file19;
	private FormFile file20;
	private FormFile file21;
	private FormFile file22;
	private FormFile file23;
	private FormFile file24;
	private FormFile file25;
	private FormFile file26;
	private FormFile file27;
	private FormFile file28;
	private FormFile file29;
	private FormFile file30;
	private FormFile file31;
	private FormFile file32;
	private FormFile file33;
	private FormFile file34;
	private FormFile file35;
	private FormFile file36;
	private FormFile file37;
	private FormFile file38;
	private FormFile file39;
	private FormFile file40;
	private FormFile file41;
	private FormFile file42;
	private FormFile file43;
	private FormFile file44;
	private FormFile file45;
	private FormFile file46;
	private FormFile file47;
	private FormFile file48;
	private FormFile file49;
	private FormFile file50;

	public FormFile getFile(int tag) {
		if(tag == 1){
			return file1;
		}else if(tag == 2){
			return file2;
		}else if(tag == 3){
			return file3;
		}else if(tag == 4){
			return file4;
		}else if(tag == 5){
			return file5;
		}else if(tag == 6){
			return file6;
		}else if(tag == 7){
			return file7;
		}else if(tag == 8){
			return file8;
		}else if(tag == 9){
			return file9;
		}else if(tag == 10){
			return file10;
		}else if(tag == 11){
			return file11;
		}else if(tag == 12){
			return file12;
		}else if(tag == 13){
			return file13;
		}else if(tag == 14){
			return file14;
		}else if(tag == 15){
			return file15;
		}else if(tag == 16){
			return file16;
		}else if(tag == 17){
			return file17;
		}else if(tag == 18){
			return file18;
		}else if(tag == 19){
			return file19;
		}else if(tag == 20){
			return file20;
		}else if(tag == 21){
			return file21;
		}else if(tag == 22){
			return file22;
		}else if(tag == 23){
			return file23;
		}else if(tag == 24){
			return file24;
		}else if(tag == 25){
			return file25;
		}else if(tag == 26){
			return file26;
		}else if(tag == 27){
			return file27;
		}else if(tag == 28){
			return file28;
		}else if(tag == 29){
			return file29;
		}else if(tag == 30){
			return file30;
		}else if(tag == 31){
			return file31;
		}else if(tag == 32){
			return file32;
		}else if(tag == 33){
			return file33;
		}else if(tag == 34){
			return file34;
		}else if(tag == 35){
			return file35;
		}else if(tag == 36){
			return file36;
		}else if(tag == 37){
			return file37;
		}else if(tag == 38){
			return file38;
		}else if(tag == 39){
			return file39;
		}else if(tag == 40){
			return file40;
		}else if(tag == 41){
			return file41;
		}else if(tag == 42){
			return file42;
		}else if(tag == 43){
			return file43;
		}else if(tag == 44){
			return file44;
		}else if(tag == 45){
			return file45;
		}else if(tag == 46){
			return file46;
		}else if(tag == 47){
			return file47;
		}else if(tag == 48){
			return file48;
		}else if(tag == 49){
			return file49;
		}else if(tag == 50){
			return file50;
		}else {
			return file;
		}
	}
	
	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public FormFile getFile1() {
		return file1;
	}

	public void setFile1(FormFile file1) {
		this.file1 = file1;
	}

	public FormFile getFile2() {
		return file2;
	}

	public void setFile2(FormFile file2) {
		this.file2 = file2;
	}

	public FormFile getFile3() {
		return file3;
	}

	public void setFile3(FormFile file3) {
		this.file3 = file3;
	}

	public FormFile getFile4() {
		return file4;
	}

	public void setFile4(FormFile file4) {
		this.file4 = file4;
	}

	public FormFile getFile5() {
		return file5;
	}

	public void setFile5(FormFile file5) {
		this.file5 = file5;
	}

	public FormFile getFile6() {
		return file6;
	}

	public void setFile6(FormFile file6) {
		this.file6 = file6;
	}

	public FormFile getFile7() {
		return file7;
	}

	public void setFile7(FormFile file7) {
		this.file7 = file7;
	}

	public FormFile getFile8() {
		return file8;
	}

	public void setFile8(FormFile file8) {
		this.file8 = file8;
	}

	public FormFile getFile9() {
		return file9;
	}

	public void setFile9(FormFile file9) {
		this.file9 = file9;
	}

	public FormFile getFile10() {
		return file10;
	}

	public void setFile10(FormFile file10) {
		this.file10 = file10;
	}

	public FormFile getFile11() {
		return file11;
	}

	public void setFile11(FormFile file11) {
		this.file11 = file11;
	}

	public FormFile getFile12() {
		return file12;
	}

	public void setFile12(FormFile file12) {
		this.file12 = file12;
	}

	public FormFile getFile13() {
		return file13;
	}

	public void setFile13(FormFile file13) {
		this.file13 = file13;
	}

	public FormFile getFile14() {
		return file14;
	}

	public void setFile14(FormFile file14) {
		this.file14 = file14;
	}

	public FormFile getFile15() {
		return file15;
	}

	public void setFile15(FormFile file15) {
		this.file15 = file15;
	}

	public FormFile getFile16() {
		return file16;
	}

	public void setFile16(FormFile file16) {
		this.file16 = file16;
	}

	public FormFile getFile17() {
		return file17;
	}

	public void setFile17(FormFile file17) {
		this.file17 = file17;
	}

	public FormFile getFile18() {
		return file18;
	}

	public void setFile18(FormFile file18) {
		this.file18 = file18;
	}

	public FormFile getFile19() {
		return file19;
	}

	public void setFile19(FormFile file19) {
		this.file19 = file19;
	}

	public FormFile getFile20() {
		return file20;
	}

	public void setFile20(FormFile file20) {
		this.file20 = file20;
	}

	public FormFile getFile21() {
		return file21;
	}

	public void setFile21(FormFile file21) {
		this.file21 = file21;
	}

	public FormFile getFile22() {
		return file22;
	}

	public void setFile22(FormFile file22) {
		this.file22 = file22;
	}

	public FormFile getFile23() {
		return file23;
	}

	public void setFile23(FormFile file23) {
		this.file23 = file23;
	}

	public FormFile getFile24() {
		return file24;
	}

	public void setFile24(FormFile file24) {
		this.file24 = file24;
	}

	public FormFile getFile25() {
		return file25;
	}

	public void setFile25(FormFile file25) {
		this.file25 = file25;
	}

	public FormFile getFile26() {
		return file26;
	}

	public void setFile26(FormFile file26) {
		this.file26 = file26;
	}

	public FormFile getFile27() {
		return file27;
	}

	public void setFile27(FormFile file27) {
		this.file27 = file27;
	}

	public FormFile getFile28() {
		return file28;
	}

	public void setFile28(FormFile file28) {
		this.file28 = file28;
	}

	public FormFile getFile29() {
		return file29;
	}

	public void setFile29(FormFile file29) {
		this.file29 = file29;
	}

	public FormFile getFile30() {
		return file30;
	}

	public void setFile30(FormFile file30) {
		this.file30 = file30;
	}

	public FormFile getFile31() {
		return file31;
	}

	public void setFile31(FormFile file31) {
		this.file31 = file31;
	}

	public FormFile getFile32() {
		return file32;
	}

	public void setFile32(FormFile file32) {
		this.file32 = file32;
	}

	public FormFile getFile33() {
		return file33;
	}

	public void setFile33(FormFile file33) {
		this.file33 = file33;
	}

	public FormFile getFile34() {
		return file34;
	}

	public void setFile34(FormFile file34) {
		this.file34 = file34;
	}

	public FormFile getFile35() {
		return file35;
	}

	public void setFile35(FormFile file35) {
		this.file35 = file35;
	}

	public FormFile getFile36() {
		return file36;
	}

	public void setFile36(FormFile file36) {
		this.file36 = file36;
	}

	public FormFile getFile37() {
		return file37;
	}

	public void setFile37(FormFile file37) {
		this.file37 = file37;
	}

	public FormFile getFile38() {
		return file38;
	}

	public void setFile38(FormFile file38) {
		this.file38 = file38;
	}

	public FormFile getFile39() {
		return file39;
	}

	public void setFile39(FormFile file39) {
		this.file39 = file39;
	}

	public FormFile getFile40() {
		return file40;
	}

	public void setFile40(FormFile file40) {
		this.file40 = file40;
	}

	public FormFile getFile41() {
		return file41;
	}

	public void setFile41(FormFile file41) {
		this.file41 = file41;
	}

	public FormFile getFile42() {
		return file42;
	}

	public void setFile42(FormFile file42) {
		this.file42 = file42;
	}

	public FormFile getFile43() {
		return file43;
	}

	public void setFile43(FormFile file43) {
		this.file43 = file43;
	}

	public FormFile getFile44() {
		return file44;
	}

	public void setFile44(FormFile file44) {
		this.file44 = file44;
	}

	public FormFile getFile45() {
		return file45;
	}

	public void setFile45(FormFile file45) {
		this.file45 = file45;
	}

	public FormFile getFile46() {
		return file46;
	}

	public void setFile46(FormFile file46) {
		this.file46 = file46;
	}

	public FormFile getFile47() {
		return file47;
	}

	public void setFile47(FormFile file47) {
		this.file47 = file47;
	}

	public FormFile getFile48() {
		return file48;
	}

	public void setFile48(FormFile file48) {
		this.file48 = file48;
	}

	public FormFile getFile49() {
		return file49;
	}

	public void setFile49(FormFile file49) {
		this.file49 = file49;
	}

	public FormFile getFile50() {
		return file50;
	}

	public void setFile50(FormFile file50) {
		this.file50 = file50;
	}

	public FormFile getContent() {
		return content;
	}

	public void setContent(FormFile content) {
		this.content = content;
	}

	public FormFile getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(FormFile htmlContent) {
		this.htmlContent = htmlContent;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}
