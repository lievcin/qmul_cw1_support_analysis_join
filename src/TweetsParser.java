import org.apache.hadoop.io.Text;
import java.text.Normalizer;

public class TweetsParser {

	private long epoch;
	private String tweetId;
	private String tweetBody;
	private String device;
	private boolean valid_structure;
	private String[] tweet_parts;

	public void parse(String tweet) {

		tweet_parts = tweet.toString().split("\\;");

		if (tweet_parts.length == 4) {
			//the condition above is due to a series of tweets that have no id somehow or are corrupted
			//but they have 4 elements as ; is being used inside the text of the tweet itself.

			try {
        epoch = Long.parseLong(tweet_parts[0]);
				tweetId = tweet_parts[1];
				//there would be more work to do here to remove some accents as well as non-standard characters
				//however due to the scope of the exercise this is not really addressed in this task.
				tweetBody = tweet_parts[2];
				device = tweet_parts[3];
				valid_structure = true;
      } catch(Exception e) {
      	valid_structure  = false; //unexpected characters in a field (like text in an epoch)
  	  }

		}
		else {
			valid_structure  = false; // when split not getting valid number of fields
		}

	}

	public void parse(Text tweet) {
		parse(tweet.toString());
	}

	public String getTweetBody() {
		return tweetBody;
	}

	public int getTweetLength() {
		return tweetBody.length();
	}

	public int getTweetBinSize() {
		return (int) Math.floor(getTweetLength()/5);
	}

	public boolean isValidTweet() {

		if (valid_structure == false || getTweetLength() > 140 ) {
			return false;
		}
		else {
			return true;
		}

	}
}