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

public class ShelterRefine {

		public static class TokenizerMapper
			extends Mapper<Object,Text,Text,Text> {

		// variable declairations
		private Text word = new Text();
		private Text name = new Text();
		// map function (Context -> fixed parameter)
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			// value.toString() : get a line
			  String[] arr = value.toString().split(",");
			  name.set(arr[0]);
			  String line = "";
			  line += arr[0]+", "; // 이름
			  line += arr[3]+", "; // 장소유형
			  line += arr[4]+", "; // 도로명주소
			  line += arr[6]+", "; // 위도
			  line += arr[7]+", "; // 경도
			  line += arr[9]+", "; // 수용인원
			  line += arr[10]+", "; //운영여부
			  word.set(line);
			  context.write(name,word);
		}
	}
	}


	
	static int key = 0;
	public static class ResultTotalReducer
			extends Reducer<Text,Text,IntWritable,BSONWritable> {

		public void reduce(IntWritable key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {

			BasicBSONObject output = new BasicBSONObject();
			BSONWritable reduceResult = new BSONWritable();

			String line = "";
			for ( Text val : values ) {
				line += val.toString();
			}
			
			output.put(key.toString(), Integer.toString(sum));
			reduceResult.setDoc(output);
			context.write(key++, reduceResult);
		}
	}


	/* Main function */
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		MongoConfigUtil.setOutputURI(conf, "mongodb://<mongoDB IP address>/<database name>.<collection name>"); 
		
		Job job = new Job(conf,"Shelter Refine");
		job.setJarByClass(ShelterRefine.class);

		// mapper & reducer setting
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(ResultTotalReducer.class);

		// output type
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(BSONWritable.class);

		// reduce 2 ea

		job.setNumReduceTasks(2);

		// set input and output directories
		FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
		job.setOutputFormatClass(MongoOutputFormat.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1 );
	}
}

