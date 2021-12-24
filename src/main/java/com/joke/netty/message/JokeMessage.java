package com.joke.netty.message;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class JokeMessage {


    private static final Charset charset = StandardCharsets.UTF_8;

    private int length;

    private byte[] msgByte;

    private String msg;

    public JokeMessage(byte[] message) {
        this.msgByte = message;
        this.length = message.length;
    }

    public JokeMessage(String msg) {
        this.msgByte = msg.getBytes(charset);
        this.length = msgByte.length;
    }

    public String getMsg() {
        return Objects.nonNull(msg) ? msg : new String(msgByte, charset);
    }


    private byte[] concatByte(byte[]... bytes) {
        int sum = Arrays.stream(bytes).mapToInt(e -> e.length).sum();
        byte[] result = new byte[sum];
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            System.arraycopy(bytes[i], 0, result, index, bytes[i].length);
            i += bytes[i].length;
        }
        return result;
    }
}
