package com.ale.utils;

public class RabbitConstant {

    // work
    public static final String WORK_EXCHANGE = "ale.work";
    public static final String WORK_QUEUE = "work_queue";

    // fanout
    public static final String FANOUT_EXCHANGE = "ale.fanout";
    // 目前使用临时队列
    public static final String FANOUT_QUEUE_01 = "fanout_01";
    public static final String FANOUT_QUEUE_02 = "fanout_02";

    // direct
    public static final String DIRECT_EXCHANGE = "ale.direct";

    public static final String DIRECT_QUEUE_ORANGE_GREEN = "direct_orange_green";
    public static final String DIRECT_QUEUE_BLACK = "direct_black";

    public static final String DIRECT_ROUTING_KEY_GREEN = "green";
    public static final String DIRECT_ROUTING_KEY_ORANGE = "orange";
    public static final String DIRECT_ROUTING_KEY_BLACK = "black";

    // log 消息配置
    public static final String LOG_TOPIC_EXCHANGE = "log.topic";

    public static final String LOG_TOPIC_QUEUE_ERROR = "log_error";
    public static final String LOG_TOPIC_QUEUE_INFO_WARN = "log_info_warn";
    public static final String LOG_TOPIC_QUEUE_ALL = "log_all";

    // * 号通配符可以指定【只匹配一个】个路由值，无法收到 log.error 的消息
    public static final String LOG_ROUTING_KEY_ERROR = "log.*.error";
    public static final String LOG_ROUTING_KEY_INFO = "log.info";
    public static final String LOG_ROUTING_KEY_WARN = "log.warn";
    // * 号通配符可以指定【只匹配一个】个路由值
    // # 号通配符可以表示【0 or 多】个路由值
    public static final String LOG_ROUTING_KEY_ALL = "log.#";

    // order 队列配置
    public static final String ORDER_EXCHANGE = "ale.order";
    public static final String ORDER_QUEUE = "order_queue";
    public static final String ORDER_ROUTING_KEY = "order.#";

    // user 队列配置
    public static final String USER_EXCHANGE = "ale.user";
    public static final String USER_QUEUE = "user_queue";
    public static final String USER_ROUTING_KEY = "user.#";

    // 死信队列配置
    public static final String DEAD_LETTER_EXCHANGE = "ale.deadLetter";
    public static final String DEAD_LETTER_QUEUE_USER = "dead_user";
    public static final String DEAD_LETTER_QUEUE_ORDER = "dead_order";

    public static final String DEAD_LETTER_ROUTING_KEY_ORDER = "dead.order";
    public static final String DEAD_LETTER_ROUTING_KEY_USER = "dead.user";



}
