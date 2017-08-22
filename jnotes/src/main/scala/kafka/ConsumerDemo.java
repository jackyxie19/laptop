package kafka;



import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by jacky on 2017/7/13.
 */
public class ConsumerDemo {
    public static void main(String args[]){
        Config conf = ConfigFactory.load();
        Properties props = new Properties();
        props.put("bootstrap.servers", conf.getString("KAFKA_PUBLIC.BROKERS"));
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        subscribeOne(consumer);
        subscribeTwo(consumer);
        assignTwo(consumer);
        assignOne(consumer);
    }

    private static void assignOne(KafkaConsumer<String, String> consumer) {
        Config conf = ConfigFactory.load();
        String topic = conf.getString("KAFKA_TOPIC.MONDAY");
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        consumer.assign(Arrays.asList(partition0, partition1));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
//                insertIntoDb(buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    private static void assignTwo(KafkaConsumer<String, String> consumer) {
        Config conf = ConfigFactory.load();
        String topic = conf.getString("KAFKA_TOPIC.MONDAY");
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        consumer.assign(Arrays.asList(partition0, partition1));
        try {
            Boolean running = true;
            while(running) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);//poll拉取数据
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);//每个分区的数据集合
                    for (ConsumerRecord<String, String> record : partitionRecords) {//分区中单条记录
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));//偏移量+1
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static void subscribeTwo(KafkaConsumer<String, String> consumer) {
        consumer.subscribe(Arrays.asList("foo", "bar"));
        try {
            Boolean running = true;
            while(running) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);//poll拉取数据
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);//每个分区的数据集合
                    for (ConsumerRecord<String, String> record : partitionRecords) {//分区中单条记录
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));//偏移量+1
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static void subscribeOne(KafkaConsumer<String, String> consumer) {
        consumer.subscribe(Arrays.asList("foo", "bar"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
//                insertIntoDb(buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }
    }
}
