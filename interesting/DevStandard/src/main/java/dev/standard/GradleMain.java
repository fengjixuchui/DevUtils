package dev.standard;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * detail: 获取 Gradle 文件夹下随机名字
 * @author Ttt
 * <pre>
 *     Gradle 缓存目录文件命名规则
 *     @see <a href="https://www.cnblogs.com/rainboy2010/p/7062279.html"/>
 * </pre>
 */
class GradleMain {

    public static void main(String[] args) {
        String data = getFileName("https://services.gradle.org/distributions/gradle-4.1-all.zip");
        System.out.println(data); // bzyivzo6n839fup2jbap0tjew
    }

    public static String getFileName(final String distributionUrl) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(distributionUrl.getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(36);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}