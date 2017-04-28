package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

	public static String makeThumnail(String uploadPath, String filename) throws IOException {
		String thumnailName = "";

		// 원본이미지 가져오기
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath, filename)); // upload안에
																				// filename뽑아서
																				// sourceImg에
																				// 주입

		// 썸네일이미지 데이타만들기 , 썸네일 이미지의 높이를 뒤의 100px로 동일하게 만둠
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		// 썸네일 파일명 - c://zzz/upload/2017/04/24/s_~~~~~~.jpg
		thumnailName = uploadPath + "/" + "s_" + filename; // s_붙으면 썸네일이다

		File newFile = new File(thumnailName); // 빈파일만들어줌
		String format = filename.substring(filename.lastIndexOf(".") + 1);// 확장자찾기

		// 섬네일 이미지를 썸네일 파일에 넣기
		// 썸네일 경로/파일 이름에 리사이징된 이미지를 넣는다
		ImageIO.write(destImg, format.toLowerCase(), newFile);

		return "s_" + filename;
	}

	// uplaod,thum 둘다
	public static String uploadFile(String uploadPath, // c:/zzz/upload
									String originalName, // aaa.png
									byte[] fileData) // file data
									throws Exception {
		// upload
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + originalName;

		// 한폴더에 저장할수있는 용량이제한되잇으므로 날짜별로 업로드되도록처리
		String savedPath = calcPath(uploadPath); // 최종저장될 날짜폴더리턴해줌
		File target = new File(uploadPath + savedPath, savedName);
		FileCopyUtils.copy(fileData, target);

		// thumnail
		String thumFile = UploadFileUtils.makeThumnail(uploadPath + savedPath, savedName);

		// thumnail 이미지경로 리턴
		return savedPath + "/" + thumFile;

	}

	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = "/" + cal.get(Calendar.YEAR);
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH) + 1);
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));
		makeDir(uploadPath, yearPath, monthPath, datePath);
		return datePath; // 최종파일이 저장될 경로. uploadpath는 빠져있음
	}

	public static void makeDir(String uploadPath, String... paths) {
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
		}
	}
}