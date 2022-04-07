package ssafy;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import org.bson.BasicBSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.util.MongoConfigUtil;
import com.mongodb.hadoop.io.BSONWritable;

public class Wordcount {
	/* 
	Object, Text : input key-value pair type (always same (to get a line of input file))
	Text, IntWritable : output key-value pair type
	*/
	public static class TokenizerMapper
			extends Mapper<Object,Text,Text,IntWritable> {

		// variable declairations
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		// map function (Context -> fixed parameter)
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			// value.toString() : get a line
			StringTokenizer itr = new StringTokenizer(value.toString(),",");
			while ( itr.hasMoreTokens() ) {
				word.set(itr.nextToken());

				// emit a key-value pair
				context.write(word,one);
			}
		}
	}

	/*
	Text, IntWritable : input key type and the value type of input value list
	Text, IntWritable : output key-value pair type
	*/
	private static int k =1;
	public static class IntSumReducer
			extends Reducer<Text,IntWritable,Integer,BSONWritable> {

		// variables
		// private IntWritable result = new IntWritable();
		BSONWritable reduceResult = new BSONWritable();
		
		// key : a disticnt word
		// values :  Iterable type (data list)
		public void reduce(Text key,  Iterable<IntWritable> values, Context context) 
				throws IOException, InterruptedException {
			
			BasicBSONObject output = new BasicBSONObject();

			int sum = 0;
			for ( IntWritable val : values ) {
				sum += val.get();
			}

			// output.put(key.toString(),Integer.toString(sum));
			output.put("word",key.toString());
			output.put("count",sum);
			reduceResult.setDoc(output);
			context.write(k++,reduceResult);
		}
	}


	/* Main function */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		//결과가 담길 몽고디비 설정
		MongoConfigUtil.setOutputURI(conf,"mongodb://threestar:ssafya403threestar@j6a403.p.ssafy.io:27017/shelter.wordcloud?authSource=admin&authMechanism=SCRAM-SHA-1");

		 String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		// if ( otherArgs.length != 2 ) {
		// 	System.err.println("Usage: <in> <out>");
		// 	System.exit(2);
		// }
		Job job = new Job(conf,"word count");
		job.setJarByClass(Wordcount.class);

		// let hadoop know my map and reduce classes
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(IntSumReducer.class);

		//map과 reduce의 인풋 아웃풋 형식이 다르기 때문에 남겨놔야한다.
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		//reduce 형식
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(BSONWritable.class);

		// set number of reduces
		job.setNumReduceTasks(2);

		// set input and output directories
		FileInputFormat.addInputPath(job,new Path(otherArgs[0]));

		//결과물을 저장할 MongoOutputFormat
		job.setOutputFormatClass(MongoOutputFormat.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1 );
	}
}

