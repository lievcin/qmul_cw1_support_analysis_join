	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URI;
	import java.util.Hashtable;
	import java.util.Set;

	import org.apache.hadoop.fs.FSDataInputStream;
	import org.apache.hadoop.fs.FileSystem;
	import org.apache.hadoop.fs.Path;
	import org.apache.hadoop.io.Text;
	import org.apache.hadoop.io.IntWritable;
	import org.apache.hadoop.mapreduce.Mapper;


	public class SupportMapper extends Mapper<Object, Text, TextPair, IntWritable> {

		private Hashtable<String, String> athletesData;
		private final IntWritable one = new IntWritable(1);
		private TweetsParser parser = new TweetsParser();
    private TextPair pair = new TextPair();

    public void map(Object key, Text tweet, Context context) throws IOException, InterruptedException {

  		parser.parse(tweet);

  		if (parser.isValidTweet()) {

  			Set<String> athlete_names = athletesData.keySet();
  			for(String athlete_name: athlete_names){
            if (parser.getTweetBody().toLowerCase().contains(athlete_name.toLowerCase())) {
            	//this would mean the athlete name is present in the text of the tweet.
            	Text ath_name = new Text(athlete_name);
            	Text sport = new Text(athletesData.get(athlete_name));
            	pair.set(ath_name, sport);
            	//we set the pair to be the athlete name and sport, with a count of 1 to aggregate in our reducers.
            	context.write(pair, one);
            }
        }
  		}
    }

    //As provided in lab 4 files to create a Hashtable
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {

			athletesData = new Hashtable<String, String>();

			// We know there is only one cache file, so we only retrieve that URI
			URI fileUri = context.getCacheFiles()[0];

			FileSystem fs = FileSystem.get(context.getConfiguration());
			FSDataInputStream in = fs.open(new Path(fileUri));

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = null;
			try {
				// we discard the header row
				br.readLine();

				while ((line = br.readLine()) != null) {
					context.getCounter(CustomCounters.NUM_ATHLETES).increment(1);

					String[] fields = line.split("\\,");
					// id,name,nationality,sex,dob,height,weight,sport,gold,silver,bronze
					// 491565031,Michael Phelps,USA,male,6/30/1985,1.94,90,aquatics,5,1,0
					if (fields.length == 11)
						athletesData.put(fields[1], fields[7]);
				}
				br.close();
			} catch (IOException e1) {
			}

			super.setup(context);
		}

	}
