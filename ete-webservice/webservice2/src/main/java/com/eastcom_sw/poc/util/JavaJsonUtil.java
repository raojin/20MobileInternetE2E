package com.eastcom_sw.poc.util;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class JavaJsonUtil {
    private static Gson gson = new Gson();

    public JavaJsonUtil() {
    }

    public static String beanToJson(Object o) {
        try {
            return gson.toJson(o);
        } catch (Exception var2) {
            var2.printStackTrace();
            return JSONObject.fromObject(o).toString();
        }
    }

    public static String arrayToJson(Object o) {
        return JSONArray.fromObject(o).toString();
    }

    public static Object jsonToBean(String json) {
        return jsonToBean(json, JSONObject.class);
    }

    public static Object jsonToBean(String json, Class clazz) {
        JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(json);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(clazz);
        return JSONSerializer.toJava(jsonObject, jsonConfig);
    }

    public static Object jsonToBean(String json, Class clazz, Map map) {
        if(map == null) {
            return jsonToBean(json, clazz);
        } else {
            JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(json);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setRootClass(clazz);
            jsonConfig.setClassMap(map);
            return JSONSerializer.toJava(jsonObject, jsonConfig);
        }
    }

    public static Object jsonToBeanWithCollection(String json, Class clazz, String collectionName, Class clazzType) {
        HashMap classMap = new HashMap();
        classMap.put(collectionName, clazzType);
        return jsonToBean(json, clazz, classMap);
    }

    public static Object jsonToBeanWithCollection(String json, Class clazz, Map<String, Class> classMap) {
        return jsonToBean(json, clazz, classMap);
    }

    public static Object applyFieldsToBean(JSONObject o1, Object o2) {
        Set keys = o1.keySet();
        Class clz = o2.getClass();
        Object[] emptyArgs = new Object[0];
        Iterator i$ = keys.iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            Method getMethod = null;

            try {
                getMethod = clz.getMethod(getMethodName(key, "get"), new Class[0]);
            } catch (NoSuchMethodException var17) {
                var17.printStackTrace();

                try {
                    getMethod = clz.getMethod(getMethodName(key, "is"), new Class[0]);
                } catch (NoSuchMethodException var15) {
                    var15.printStackTrace();
                } catch (SecurityException var16) {
                    var16.printStackTrace();
                }
            } catch (SecurityException var18) {
                var18.printStackTrace();
            }

            if(getMethod != null) {
                try {
                    Method e = clz.getMethod(getMethodName(key, "set"), new Class[]{getMethod.getReturnType()});
                    e.invoke(o2, new Object[]{o1.get(key)});
                } catch (NoSuchMethodException var10) {
                    var10.printStackTrace();
                } catch (SecurityException var11) {
                    var11.printStackTrace();
                } catch (IllegalAccessException var12) {
                    var12.printStackTrace();
                } catch (IllegalArgumentException var13) {
                    var13.printStackTrace();
                } catch (InvocationTargetException var14) {
                    var14.printStackTrace();
                }
            }
        }

        return o2;
    }

    public static String getMethodName(String fieldName, String head) {
        String name = head + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return name;
    }

    public static String[] getFieldName(String methodName) {
        String name = "";
        String head = "";
        if(methodName.startsWith("get")) {
            head = methodName.substring(0, 3);
            name = methodName.substring(3);
        } else if(methodName.startsWith("is")) {
            head = methodName.substring(0, 2);
            name = methodName.substring(2);
        }

        if(name!=null&&!"".equals(name)) {
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }

        String[] arr = new String[]{head, name};
        return arr;
    }
}
