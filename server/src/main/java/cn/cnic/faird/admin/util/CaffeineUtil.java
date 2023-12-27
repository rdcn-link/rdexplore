package cn.cnic.faird.admin.util;

import algebra.lattice.Bool;
import cn.cnic.protocol.model.DataFrame;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * CaffeineUtil
 *
 * @author jmal
 */
public class CaffeineUtil {

    /***
     *  缓存
     */
    public final static Cache<String, Boolean> DATAFRAME_IS_OPEN = Caffeine.newBuilder().expireAfterWrite(1,TimeUnit.DAYS).build();

    public final static Cache<String, DataFrame> DATAFRAME_CACHE = Caffeine.newBuilder().expireAfterWrite(1,TimeUnit.DAYS).build();


    public static String generateKey(String uri, String firstIndex, String secondIndex) {
        return uri + "-" + firstIndex + "-" + secondIndex;
    }

}
