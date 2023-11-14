package avgTemp;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AvgTemperatureMapper
extends Mapper<LongWritable, Text, Text, IntWritable> {

private static final int MISSING = 9999;

@Override
public void map(LongWritable key, Text value, Context context)
   throws IOException, InterruptedException {
   // Your mapper implementation here
        String line = value.toString();

        String date = line.substring(15, 21);
        String hour = line.substring(23, 25); // Extract hour (HH) from HHMMSS

        int airTemperature;
        char sign = line.charAt(87);

        if (sign == '+') {
            airTemperature = Integer.parseInt(line.substring(88, 92));
        } else {
            airTemperature = Integer.parseInt(line.substring(87, 92));
        }

        String quality = line.substring(92, 93);

        if ("13".equals(hour) && airTemperature != MISSING && "01459".contains(quality)) {
            context.write(new Text(date), new IntWritable(airTemperature));
        }
    }
}

