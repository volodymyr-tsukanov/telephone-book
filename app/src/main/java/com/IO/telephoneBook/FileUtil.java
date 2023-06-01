package com.IO.telephoneBook;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class FileUtil {
	private static void createNewFile(String path) {
		int lastSep = path.lastIndexOf(File.separator);
		if (lastSep > 0) {
			String dirPath = path.substring(0, lastSep);
			makeDir(dirPath);
		}

		File file = new File(path);

		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String path) {
		createNewFile(path);

		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		try {
			fr = new FileReader(new File(path));

			char[] buff = new char[1024];
			int length = 0;

			while ((length = fr.read(buff)) > 0) {
				sb.append(new String(buff, 0, length));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public static void writeFile(String path, String str) {
		createNewFile(path);
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(new File(path), false);
			fileWriter.write(str);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyFile(String sourcePath, String destPath) {
		if (!isExistFile(sourcePath)) return;
		createNewFile(destPath);

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(sourcePath);
			fos = new FileOutputStream(destPath, false);

			byte[] buff = new byte[1024];
			int length = 0;

			while ((length = fis.read(buff)) > 0) {
				fos.write(buff, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void moveFile(String sourcePath, String destPath) {
		copyFile(sourcePath, destPath);
		deleteFile(sourcePath);
	}

	public static void deleteFile(String path) {
		File file = new File(path);

		if (!file.exists()) return;

		if (file.isFile()) {
			file.delete();
			return;
		}

		File[] fileArr = file.listFiles();

		if (fileArr != null) {
			for (File subFile : fileArr) {
				if (subFile.isDirectory()) {
					deleteFile(subFile.getAbsolutePath());
				}

				if (subFile.isFile()) {
					subFile.delete();
				}
			}
		}

		file.delete();
	}


	public static void makeDir(String path) {
		if (!isExistFile(path)) {
			File file = new File(path);
			file.mkdirs();
		}
	}
	

	public static boolean isDirectory(String path) {
		if (!isExistFile(path)) return false;
		return new File(path).isDirectory();
	}

	public static boolean isFile(String path) {
		if (!isExistFile(path)) return false;
		return new File(path).isFile();
	}
	
	
	public static boolean isExistFile(String path) {
		File file = new File(path);
		return file.exists();
	}


	public static long getFileLength(String path) {
		if (!isExistFile(path)) return 0;
		return new File(path).length();
	}


	public static String getExternalStorageDir() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getPackageDataDir(Context context) {
		return context.getExternalFilesDir(null).getAbsolutePath();
	}

	public static String getPublicDir(String type) {
		return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
	}
}
