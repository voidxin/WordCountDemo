目标：统计文件中指定字符串出现的次数，如计算f1.txt中“voidxin”出现了几次。
1、maper类如下：
```
public class VXWordCountMapper extends Mapper<LongWritable,Text , Text, Text> {
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String keyStr = key.toString();
		context.write(new Text(keyStr), new Text(line));
	}

}
```

2、reducer类的实现如下：
```
public class VXWordCountReducer extends Reducer<Text, Text, Text, IntWritable> {
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		int count = 0;
		
		String targetStr = "voidxin";
		for (Text textWritable : values) {
	    	
	    	@SuppressWarnings("resource")
			Scanner scan = new Scanner(textWritable.toString()).useDelimiter(" |,|\\?|\\.|'");
	    	while(scan.hasNext()) {
	    		if(scan.next().equals(targetStr)) {
	    			count ++;
	    		}
	    	}
			
		}

		context.write(key, new IntWritable(count));

	}

}
```

3、main函数实现如下：
```
public class VXWordCount {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = new Job();
		job.setJarByClass(VXWordCount.class);
		job.setJobName("word count");

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
		job.setMapperClass(VXWordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setReducerClass(VXWordCountReducer.class);
       
		// 等待作业完成
		System.out.println(job.waitForCompletion(true));
	}
}

```

最后导出jar包，运行：
1:从mac本地拷贝到linux虚拟机系统指定的文件夹中，之后的命令输入都是基于在testfile目录下的
```
cp ***.jar /home/testfile 
```
2:编辑需要统计的文件，vi f1.txt
```
ni zhi dao wo shi shui ma ? my name is voidxin 
voidxin do you know?
wo shi shui,ni shishui a 
are you,voidxin'voidxin.
hahahahahahahahah
nishi

```
3:把f1.txt上传,
```
hadoop fs -put f1.txt  /home/input
```



4：运行(注意输出目录之前不能存在)
```
hadoop jar ***.jar /home/input/f1/txt  /home/wordcountDemo 
```
![QQ20170616-2.png](http://upload-images.jianshu.io/upload_images/1376067-def17e601428f2b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

5:结果如下、

![QQ20170616-1.png](http://upload-images.jianshu.io/upload_images/1376067-336f17fd182c6e32.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




其中最要的核心代码应该就这几行：
```
for (Text textWritable : values) {
	    	
	    	@SuppressWarnings("resource")
			Scanner scan = new Scanner(textWritable.toString()).useDelimiter(" |,|\\?|\\.|'");
	    	while(scan.hasNext()) {
	    		if(scan.next().equals(targetStr)) {
	    			count ++;
	    		}
	    	}
			
		}
```

文章请前往简书：http://www.jianshu.com/p/ac86528d48ed
