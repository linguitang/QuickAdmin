package com.xinou.quickadmin.ext;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

/**
 * Created by shizhida on 16/3/17.
 */
public class IDUtil {

    public static void main(String[] args) {
        System.out.println(generateInvitationCode());
    }
    public static String generateUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    /**
     * 生成用户ticket
     * @param id
     * @return
     */
    public static String generateTicket(String id) {
        String ticket = generateUuid()+id;
        return MD5Util.MD5(ticket);
    }

    /**
     * 生成短信验证码
     * @return
     */
    public static String generateSmsCode(){
        StringBuilder builder = new StringBuilder();
        Random random = new Random(System.nanoTime());
        for(int i=0;i<6;i++){
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    /**
     * 生成邀请码
     * 原理：将当前时间戳转换成高进制的字符串
     * @return
     */
    public static String generateInvitationCode(){

        //乱序编码表
        char[] items = {
                'w','I','0','1','2','3','4',
                'Q','R','S','T','5','6',
                'x','y','z','A','B','C','D',
                'E','F','G','H',
                'a','b','7','8','9','c','d','e',
                'p','q','r','s','t','f','g','h','i','j',
                'k','l','m','n','o',
                'u','v','J','K','L','M','N',
                'O','P','U','V','W','X',
                'Y','Z'
        };
        return toAnyMapper(System.currentTimeMillis(),items);


    }

    /**
     * 十六进制映射
     */
    public final static char[] NUMBER_MAPPER_HEX = {
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f',
    };
    /**
     * 十六进制映射：大写字母
     */
    public final static char[] NUMBER_MAPPER_HEX_CAPITAL = {
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F',
    };

    /**
     * 根据映射码将十进制数字源转换为任意进制形态的字符串
     * @param source
     * @param mapper
     * @return
     */
    public static String toAnyMapper(long source,char[] mapper){
        BigInteger bi = BigInteger.valueOf(source);
        int l = mapper.length;
        StringBuilder target = new StringBuilder();
        while(bi.intValue()!=0){
            target.insert(0,mapper[bi.remainder(BigInteger.valueOf(l)).intValue()]);
            bi = bi.divide(BigInteger.valueOf(l));
        }
        return target.toString();
    }
}
