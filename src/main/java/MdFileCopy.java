

import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* @authoer zsc
* @date 2018.09.27
* @version v1
* */

public class MdFileCopy {

    //seachFiles
    public static List<File> searchfFiles(File folder, final String keyword){
        List<File> result =new ArrayList<File>();

        if (folder.isFile())
            result.add(folder);

        /*File[] subFolders=folder.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if(file.isDirectory()){
                    return true;
                }
                if (file.getName().toLowerCase().contains(keyword)){
                    return true;
                }
                return false;
            }
        });*/

        File[] subFolders = folder.listFiles(new FileFilter() {

            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                if (file.getName().toLowerCase().endsWith(keyword)) {
                    return true;
                }
                return false;
            }
        });

        if(subFolders!=null){
            for (File file_res:subFolders){
                if(file_res.isFile()){
                    result.add(file_res);
                }else {
                    result.addAll(searchfFiles(file_res,keyword));
                }
            }
        }
        return result;
    }

    //FileCopy
    public static void fileCopy(String sourceFilePath,String targetFilePath ) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File(sourceFilePath));
        //InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
        BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
        //BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
        FileOutputStream fileOutputStream=new FileOutputStream(targetFilePath);
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
        byte[] b=new byte[1024];
        int n;
        try {
            while((n=bufferedInputStream.read(b))!=-1){
                bufferedOutputStream.write(b,0,n);
            }
        }catch (Exception e){
            System.out.println("Filed!");
        }
        finally {
            bufferedOutputStream.close();
            fileInputStream.close();
            bufferedInputStream.close();
            fileInputStream.close();
            System.out.println("成功！");
        }
    }

    //获取文件名
    public static List<String> getFileName(List<File> fileList) {

        List<String> fileName = new ArrayList<String>();

        for (File file : fileList
        ) {
            String[] filearr = file.getAbsolutePath().split("/");
            fileName.add(filearr[filearr.length-1]);
        }
        return fileName;
    }

    public static void main(String[] args) throws IOException {
        List<File> files=searchfFiles(new File("/media/data/Javaee/"),"md");
        String targetPath="/home/cloud/test/";
        List<String> fileName=getFileName(files);
        Iterator<String> it=fileName.iterator();
        int i=0;
        for (File file:files
             ) {
            String absolutePath=file.getAbsolutePath();
            while (it.hasNext()){
                i++;
                fileCopy(absolutePath,targetPath+it.next());
                System.out.println("已完成"+i+"个！");
            }
        }
    }
}
