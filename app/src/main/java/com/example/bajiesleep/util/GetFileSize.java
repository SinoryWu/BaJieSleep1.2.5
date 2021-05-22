package com.example.bajiesleep.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class GetFileSize {
    public GetFileSize() {

    }

    public long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件夹不存在");
        }

        return s;
    }

    /**
     * 递归
     * */
    public long getFileSize(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     * */
    public String FormentFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

//	/**
//	 * 递归求取目录文件个数
//	 * */
//	public long getlist(File f) {
//		long size = 0;
//		File flist[] = f.listFiles();
//		System.out.println("-------------" + flist.length);
//		size = flist.length;
//		for (int i = 0; i < flist.length; i++) {
//			if (flist[i].isDirectory()) {
//				size = size + getlist(flist[i]);
//				size--;
//			}
//		}
//		return size;
//	}
}
