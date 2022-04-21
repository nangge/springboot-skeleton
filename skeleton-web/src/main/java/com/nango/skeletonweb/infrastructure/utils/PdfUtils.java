package com.nango.skeletonweb.infrastructure.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.nango.skeletonweb.infrastructure.exception.BizException;
import com.nango.skeletonweb.infrastructure.exception.ErrorCodeEnum;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * @author nango
 * @package com.nango.skeletonweb.infrastructure.utils
 * @class PdfUtils
 * @date 2022/4/21 21:19
 * @description pdf 工具类
 */
public class PdfUtils {

    /**
     * 图片转pdf
     * @param targetFileName 转换pdf文件
     * @param imagesPath 要转换的图片路径；本地路径
     */
    public static String imagesToPdf(String targetFileName, Collection<String> imagesPath) {
        try {
            File targetFile = getTargetFilePath(targetFileName);

            //创建document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);

            // 创建Pdf实例，
            PdfWriter.getInstance(document, new FileOutputStream(targetFile));

            document.open();
            for (String filePath : imagesPath) {
                try {
                    Image image = Image.getInstance(filePath);
                    image.setAlignment(Image.ALIGN_CENTER);
                    // 设置单张pdf页面大小
                    document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
                    document.newPage();
                    document.add(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 关闭文档。
            document.close();

            //TODO 上传oss
            return targetFile.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static File getTargetFilePath(String targetFileName) {
        String tmpFolderPath = "/Users/Downloads/pdf/";
        File tmpFolder = new File(tmpFolderPath);
        if (!tmpFolder.exists()) {
            tmpFolder.mkdir();
        }

        return new File(tmpFolderPath + targetFileName + ".pdf");
    }

    /**
     *
     * @param fileName
     *
     * @param folderPath 目录
     */
    public static String imagesToPdfByFolder(String fileName, String folderPath) {

        File dir = new File(folderPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new BizException(ErrorCodeEnum.FOLDER_NOT_EXISTS);
        }

        if (Objects.isNull(dir.list()) || dir.list().length == 0) {
            throw new BizException(ErrorCodeEnum.FOLDER_NOT_EXISTS);
        }

        ArrayList<String> imgs = new ArrayList<>();
        Arrays.stream(dir.listFiles()).forEach(x->imgs.add(x.getAbsolutePath()));

        return imagesToPdf(fileName, imgs);
    }



    public static void main(String[] args)
    {
        String name = String.valueOf(System.currentTimeMillis());
        String imagesPath = "/Users/Downloads/img/";

        imagesToPdfByFolder(name, imagesPath);
//        imagesToPdf(name, Arrays.asList("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1113%2F0F420110430%2F200F4110430-6-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653143735&t=ef04f9d8ed6bfbaeb1f7df975dc17f5e"));
    }
}
