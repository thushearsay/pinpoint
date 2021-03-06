package com.navercorp.pinpoint.common.server.bo;

import com.navercorp.pinpoint.common.util.TransactionIdUtils;
import com.navercorp.pinpoint.thrift.dto.TAnnotation;
import com.navercorp.pinpoint.thrift.dto.TAnnotationValue;
import com.navercorp.pinpoint.thrift.dto.TIntStringValue;
import com.navercorp.pinpoint.thrift.dto.TSpan;
import com.navercorp.pinpoint.thrift.dto.TSpanChunk;
import com.navercorp.pinpoint.thrift.dto.TSpanEvent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Woonduk Kang(emeroad)
 */
public class RandomTSpan {

    private final Random random = new Random();

    public TSpan randomTSpan() {
        final TSpan tSpan = new TSpan();
        tSpan.setAgentId("agentId");
        tSpan.setApplicationName("appName");
        tSpan.setAgentStartTime(System.currentTimeMillis());

        tSpan.setTransactionId(TransactionIdUtils.formatByteBuffer("agent", System.currentTimeMillis(), RandomUtils.nextLong(0, Long.MAX_VALUE)));
        tSpan.setSpanId(random.nextLong());
        tSpan.setParentSpanId(RandomUtils.nextInt(0, 100000));
        tSpan.setStartTime(System.currentTimeMillis() + RandomUtils.nextInt(0, 1000));
        tSpan.setElapsed(RandomUtils.nextInt(0, 2000));
        tSpan.setRpc(RandomStringUtils.random(10));

        tSpan.setServiceType(randomServerServiceType());
        tSpan.setEndPoint(RandomStringUtils.random(20));
        tSpan.setRemoteAddr(RandomStringUtils.random(20));

        List<TAnnotation> tAnnotationList = randomTAnnotationList();
        if (CollectionUtils.isNotEmpty(tAnnotationList)) {
            tSpan.setAnnotations(tAnnotationList);
        }
        tSpan.setFlag((short) RandomUtils.nextInt(0, 4));
        tSpan.setErr((short) RandomUtils.nextInt(0, 1));
//        tSpan.setSpanEventList()
        tSpan.setParentApplicationName("parentApp");
        tSpan.setParentApplicationType(randomServerServiceType());
        tSpan.setAcceptorHost("acceptHost");
        tSpan.setApiId(RandomUtils.nextInt(0, 5000));
        tSpan.setApplicationServiceType(randomServerServiceType());
        if (random.nextBoolean()) {
            TIntStringValue exceptionInfo = new TIntStringValue();
            exceptionInfo.setIntValue(RandomUtils.nextInt(0, 5000));
            exceptionInfo.setStringValue(RandomStringUtils.random(100));
            tSpan.setExceptionInfo(exceptionInfo);
        }
        tSpan.setLoggingTransactionInfo((byte) RandomUtils.nextInt(0, 256));
        return tSpan;
    }

    private short randomServerServiceType() {
        //        Server (1000 ~ 1899)
        return (short) RandomUtils.nextInt(1000, 1899);
    }

    public List<TAnnotation> randomTAnnotationList() {
        int annotationSize = RandomUtils.nextInt(0, 3);
        List<TAnnotation> result = new ArrayList<>();
        for (int i = 0; i < annotationSize; i++) {
            result.add(randomTAnnotation(i));
        }
        return result;
    }

    public TAnnotation randomTAnnotation(int key) {
        TAnnotation tAnnotation = new TAnnotation();
        // sort order
        tAnnotation.setKey(key);
        TAnnotationValue tAnnotationValue = new TAnnotationValue();
        tAnnotationValue.setStringValue(RandomStringUtils.random(10));
        tAnnotation.setValue(tAnnotationValue);
        return tAnnotation;
    }

    public TSpanEvent randomTSpanEvent(TSpan tSpan, short sequence) {
        TSpanEvent tSpanEvent = new TSpanEvent();
//        @deprecated
//        tSpanEvent.setSpanId();
        tSpanEvent.setSequence(sequence);
        tSpanEvent.setStartElapsed(RandomUtils.nextInt(0, 1000));
        tSpanEvent.setEndElapsed(RandomUtils.nextInt(0, 1000));
        tSpanEvent.setRpc(RandomStringUtils.random(10));
//         Database (2000 ~ 2899)
        tSpanEvent.setServiceType((short) RandomUtils.nextInt(2000, 2889));
        tSpanEvent.setEndPoint(RandomStringUtils.random(10));

        List<TAnnotation> tAnnotationList = randomTAnnotationList();
        if (CollectionUtils.isNotEmpty(tAnnotationList)) {
            tSpan.setAnnotations(tAnnotationList);
        }
        tSpanEvent.setDepth(RandomUtils.nextInt(0, 256));
        tSpanEvent.setNextSpanId(random.nextLong());

        tSpanEvent.setDestinationId(RandomStringUtils.random(20));
        tSpanEvent.setApiId(RandomUtils.nextInt(0, 65535));

        tSpanEvent.setAsyncId(randomNegative(RandomUtils.nextInt(0, 10)));
        tSpanEvent.setNextAsyncId(random.nextInt());
        tSpanEvent.setAsyncSequence((short) RandomUtils.nextInt(0, Short.MAX_VALUE));

        return tSpanEvent;
    }

    private int randomNegative(int value) {
        if (random.nextBoolean()) {
            return -value;
        }
        return value;
    }

    public TSpanChunk randomTSpanChunk() {
        final TSpanChunk tSpanChunk = new TSpanChunk();
        tSpanChunk.setAgentId("agentId");
        tSpanChunk.setApplicationName("appName");
        tSpanChunk.setAgentStartTime(System.currentTimeMillis());

        tSpanChunk.setTransactionId(TransactionIdUtils.formatByteBuffer("agent", System.currentTimeMillis(), RandomUtils.nextLong(0, Long.MAX_VALUE)));
        tSpanChunk.setSpanId(random.nextLong());

        tSpanChunk.setServiceType(randomServerServiceType());
        tSpanChunk.setEndPoint(RandomStringUtils.random(20));

//        tSpanChunk.setSpanEventList()
        tSpanChunk.setApplicationServiceType(randomServerServiceType());
        return tSpanChunk;
    }
}
