package com.atguigu.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 下午3:02
 * @Version: v1.0
 * ClassName: Di
 * Package: com.atguigu.anno
 * Description: 描述
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Di {
}
