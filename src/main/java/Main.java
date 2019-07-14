import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {

        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(File.createTempFile("jas", ""), JsonEncoding.UTF8);

//        JsonGenerator jGenerator = jfactory.createGenerator(System.out, JsonEncoding.UTF8);

        PrettyPrinter a = new CononicalPrettyPrinter();
        PrettyPrinter b = new InlineConfigurableCononicalPrettyPrinter();

        PriorityQueue<Term> pQueue = new PriorityQueue<>(Collections.reverseOrder());

        jGenerator.setPrettyPrinter(b).writeStartObject();
        jGenerator.writeStringField("index", "Tom");
        jGenerator.writeNumberField("age", 25);
        jGenerator.writeFieldName("segs");
        jGenerator.writeStartArray();
        jGenerator.writeStartObject();
        jGenerator.writeStringField("name", "_1");
        jGenerator.writeFieldName("fields");
        jGenerator.writeStartArray();   // start fields array
        jGenerator.writeStartObject();
        jGenerator.writeStringField("name", "title");
        jGenerator.writeFieldName("terms");
        jGenerator.writeStartArray();   // start terms array
        ((InlineConfigurableCononicalPrettyPrinter) b).setObjectInline(true);
        pQueue.add(new Term("h3", "[31 32 33]", 30));
        pQueue.add(new Term("hi", "[11 22 33]", 10));
        pQueue.add(new Term("h2", "[21 22 23]", 20));
        while (!pQueue.isEmpty()) {
            Term t = pQueue.poll();
            jGenerator.writeStartObject();
            jGenerator.writeStringField("term", t._term);
            jGenerator.writeStringField("byte", t._byte);
            jGenerator.writeNumberField("freq", t._freq);
            jGenerator.writeEndObject();
        }
        ((InlineConfigurableCononicalPrettyPrinter) b).setObjectInline(false);
        jGenerator.writeEndArray();     // ends terms array
        jGenerator.writeEndObject();
        jGenerator.writeStartObject();
        jGenerator.writeStringField("name", "author");
        jGenerator.writeFieldName("terms");
        jGenerator.writeStartArray();

        ((InlineConfigurableCononicalPrettyPrinter) b).setObjectInline(true);
        pQueue.add(new Term("h3", "[31 32 33]", 30));
        pQueue.add(new Term("hi", "[11 22 33]", 10));
        pQueue.add(new Term("h2", "[21 22 23]", 20));
        while (!pQueue.isEmpty()) {
            Term t = pQueue.poll();
            jGenerator.writeStartObject();
            jGenerator.writeStringField("term", t._term);
            jGenerator.writeStringField("byte", t._byte);
            jGenerator.writeNumberField("freq", t._freq);
            jGenerator.writeEndObject();
        }
        ((InlineConfigurableCononicalPrettyPrinter) b).setObjectInline(false);
        jGenerator.writeEndArray();
        jGenerator.writeEndObject();
        jGenerator.writeEndArray();     // ends fields array
        jGenerator.writeEndObject();
        jGenerator.writeEndArray();
        jGenerator.writeEndObject();
        jGenerator.close();
    }
}
