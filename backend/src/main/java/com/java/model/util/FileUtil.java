package com.java.model.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
public class FileUtil implements ServletContextAware {

	private static final String RESOURCE_DIR = "/resources/temp";
	private ServletContext servletContext;

	/*
		use File.separator("\\") when access File System
		or use plain slash ("/") when handling URL
	 */
	@Nullable
	public String save(MultipartFile file) {
		String result = null;
		try {
			// make date string i.e. "20190807"
			String dateString = getDateAsString();

			// get real temporary directory path and concatenate with date string
			// i.e. "C:/.../[project_name]/resource/temp/20190807"
			String targetPath = getTempDir() + '\\' + dateString;

			// literally, make directory if absent...
			mkdirsIfAbsent(targetPath);

			String savedFilename = makeUniqueFilename(file);
			saveFile(file, targetPath + '\\' + savedFilename);

			// return location of file so that client browser access directly.
			// i.e. /resource/temp/20190807/[Unique UUID]_filename.png
			result = RESOURCE_DIR + '/' + dateString + '/' + savedFilename;
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return result;
	}

	private String getTempDir() {
		return servletContext.getRealPath(RESOURCE_DIR);
	}

	private boolean mkdirsIfAbsent(String path) {
		File file = new File(path);
		if (!file.exists())
			return file.mkdirs();
		return false;
	}

	private String makeUniqueFilename(MultipartFile file) {
		return UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
	}

	private String getDateAsString() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		DecimalFormat df = new DecimalFormat("00");
		return year + df.format(month) + df.format(date);
	}

	private void saveFile(MultipartFile file, String target) throws IOException {
		file.transferTo(new File(target));
	}

	@Override
	public void setServletContext(@NonNull ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}