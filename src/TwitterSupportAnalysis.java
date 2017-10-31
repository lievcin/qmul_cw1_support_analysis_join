import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

enum CustomCounters {NUM_ATHLETES}

public class TwitterSupportAnalysis {
    public static void runJob(String[] input, String output) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance();
        job.setJarByClass(TwitterSupportAnalysis.class);

        job.setMapperClass(SupportMapper.class);
        job.setReducerClass(SupportReducer.class);

        job.setMapOutputKeyClass(TextPair.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.addCacheFile(new Path("/data/medalistsrio.csv").toUri());
        // job.setNumReduceTasks(5);

        Path outputPath = new Path(output);
        FileInputFormat.setInputPaths(job, StringUtils.join(input, ","));
        FileOutputFormat.setOutputPath(job, outputPath);
        outputPath.getFileSystem(conf).delete(outputPath, true);
        job.waitForCompletion(true);
        }
    public static void main(String[] args) throws Exception {
        runJob(Arrays.copyOfRange(args, 0, args.length - 1), args[args.length - 1]);
    }
}