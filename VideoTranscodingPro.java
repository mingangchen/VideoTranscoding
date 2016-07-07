import java.io.IOException;
import java.util.StringTokenizer;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.text.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class VideoTranscodingPro {
    
    public static class TranscodingMapper extends Mapper<Object, Text, Text , IntWritable>{
				
	private final static IntWritable one = new IntWritable(1);
	    private Text videoPath = new Text();
		
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
			
	    String line = value.toString(); 
            System.out.println("Map key: " + key.toString()+" Map value: " + value.toString());		
	    transcoder(line);
	    videoPath.set(line);
	    context.write(videoPath, one);									
	}
		
	private static void transcoder(String path){
	    String ffmpegPath = "/media/data/ffmpeg/bin/ffmpeg ";
	    String inputPath = path;
	    String outputPath = inputPath.substring(0,inputPath.indexOf("."))+ ".avi";  
	    String cmd = ffmpegPath + "-i " + inputPath + " " + outputPath; 
	    try{
	        Process process = Runtime.getRuntime().exec(cmd);
		//InputStream stderr = process.getErrorStream();
	        //InputStreamReader isr = new InputStreamReader(stderr);
	        //BufferedReader br = new BufferedReader(isr);
	        //String line = null;
	        //while((line = br.readLine())!=null)
	        //System.out.println(line);
	        int exitVal = process.waitFor();
	        System.out.println("Process exitValue:"+ exitVal); 			    
	    }
	    catch(Throwable t){
		t.printStackTrace();
		System.out.println("Executing error!");
	    }			
	}	
    }

    public static void main(String[] args) throws Exception{
	Configuration conf = new Configuration();
	Job job = Job.getInstance(conf, "video transcoding");
	job.setJarByClass(VideoTranscodingPro.class);
	job.setMapperClass(TranscodingMapper.class);
        job.setInputFormatClass(NLineInputFormat.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	//FileInputFormat.addInputPath(job, new Path(args[0]));
        NLineInputFormat.addInputPath(job, new Path(args[0]));
        job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap",1);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);		
    }
}
