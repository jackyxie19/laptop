package kafka;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import kafka.Json.JsonGenerator;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args){
        Config conf = ConfigFactory.load();
        Properties props = new Properties();
        props.put("bootstrap.servers", conf.getString("KAFKA_PUBLIC.BROKERS"));
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = conf.getString("KAFKA_TOPIC.MONDAY");
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<>(topic, Integer.toString(i), JsonGenerator.getOneJson().toString()));
        producer.close();
        //test2();
        //test1();
    }

    /**
     * 发送json
     */
    private static void test2() {
        Config conf = ConfigFactory.load();
        Properties props = new Properties();
        props.put("bootstrap.servers", conf.getString("KAFKA_PUBLIC.BROKER"));
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = conf.getString("KAFKA_TOPIC.MONDAY");
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<>(topic, Integer.toString(i), JsonGenerator.getOneJson().toString()));
        producer.close();
    }

    private static void test1() {
        Config conf = ConfigFactory.load();
        Properties props = new Properties();
        props.put("bootstrap.servers", conf.getString("KAFKA_PUBLIC.BROKER"));
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = conf.getString("KAFKA_TOPIC.MONDAY");
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<>(topic, Integer.toString(i), Integer.toString(i)));
        producer.close();
    }
}
