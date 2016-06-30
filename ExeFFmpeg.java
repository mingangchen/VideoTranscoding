import java.io.IOException;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.text.*;

//Centos环境下Java 调用ffmpeg进行转码的示例代码
public class ExeFFmpeg {
    public static void main(String[] args){
	String ffmpegPath = "/media/data/ffmpeg/bin/ffmpeg "; //the path of ffmpeg.exe
        String cmd = ffmpegPath + "-i /media/data/videotranscoding/video1.mp4 /media/data/videotranscoding/video1.avi";	
        //System.out.println(cmd);
        System.out.println("Executing a FFMPEG Transcoding!");
	exeffmpeg(cmd);	
        System.out.println("Transcoding is finished!");
    }
	
    private static void exeffmpeg(String cmd){
	try {
	    Process process = null;
	    Runtime rt = Runtime.getRuntime();
            //System.out.println(rt.freeMemory());
	    process = rt.exec(cmd);
            InputStream stderr = process.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while((line = br.readLine())!=null)
                System.out.println(line);
            int exitVal = process.waitFor();
            System.out.println("Process exitValue:" + exitVal);
	} catch (Throwable t) {
		// TODO Auto-generated catch block
	    t.printStackTrace();
	    System.out.println("Executing Error!");
	}					
    }
}

/****
 * Process类是一个抽象类，封装了一个进程（即一个可执行程序）
 * Runtime.getRuntime().exec("") 创建一个本机进程
 * 每个Java Application都有一个Runtime类实例，使Java应用程序能够与其运行环境相连接。
 ***/
