package tina.com.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yxc
 * @date 2018/11/13
 */
@Target(ElementType.TYPE)//表示类
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTable {
    String value();
}
