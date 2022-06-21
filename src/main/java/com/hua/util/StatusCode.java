package com.hua.util;

public class StatusCode {
    public static final int OK = 200; //成功
    public static final int ERROR = 201; //失败
    public static final int LOGINERROR = 202; //用户名密码错误
    public static final int ACCESSERROR = 203; //权限不足
    public static final int LOGOFF = 207; // 用户已注销
    public static final int NOLOGED = 209; // 用户未登录
    public static final int FINDERROR = 404;
    public static final int SERVERERROR = 500; //服务器错误
}
