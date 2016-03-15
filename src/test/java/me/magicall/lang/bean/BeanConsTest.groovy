package me.magicall.lang.bean

import spock.lang.Specification

/**
 * @author Liang Wenjian
 */
class BeanConsTest extends Specification {

    def "test isGetterName"() {
        expect:
        expect_result == BeanCons.isGetterName(suppose_name)
        where:
        suppose_name | expect_result
        null         | false
        ""           | false
        " "          | false
        "get"        | false
        "getA"       | true
        "geta"       | false
        "get a"      | false
        "getA "      | false
        " getA"      | false
        "getABC"     | true
        "get什么"      | true
        "getClass"   | true
        "getCLASS"   | true
        "is"         | false
        "isA"        | true
        "isa"        | false
        "is a"       | false
        "isA "       | false
        " isA"       | false
        "isABC"      | true
        "is什么"       | true
    }

    def "test isPropGetterName"() {
        expect:
        expect_result == BeanCons.isPropGetterName(suppose_name)
        where:
        suppose_name | expect_result
        null         | false
        ""           | false
        " "          | false
        "get"        | false
        "getA"       | true
        "geta"       | false
        "get a"      | false
        "getA "      | false
        "getABC"     | true
        " getA"      | false
        "get什么"      | true
        "getClass"   | false
        "getclass"   | false
        "getCLASS"   | true
        "is"         | false
        "isA"        | true
        "isa"        | false
        "is a"       | false
        "isA "       | false
        " isA"       | false
        "isABC"      | true
        "is什么"       | true
    }

    def "test isGetter"() {
        expect:
        expect_result == BeanCons.isGetter(suppose_method)
        where:
        suppose_method                          | expect_result
        null                                    | false
        Object.getMethod("hashCode")            | false
        Object.getMethod("getClass")            | true
        Boolean.getMethod("getBoolean", String) | false
        Class.getMethod("getAnnotations")       | true
        Class.getMethod("isInterface")          | true
    }

    def "test isPropGetter"() {
        expect:
        expect_result == BeanCons.isPropGetter(suppose_method)
        where:
        suppose_method                          | expect_result
        null                                    | false
        Object.getMethod("hashCode")            | false
        Object.getMethod("getClass")            | false
        Boolean.getMethod("getBoolean", String) | false
        Class.getMethod("getAnnotations")       | true
        Class.getMethod("isInterface")          | true
    }

    def "test isSetterName"() {
        expect:
        expect_result == BeanCons.isSetterName(suppose_name)
        where:
        suppose_name | expect_result
        null         | false
        ""           | false
        " "          | false
        "set"        | false
        "setA"       | true
        "seta"       | false
        "set a"      | false
        "setA "      | false
        "setABC"     | true
        " setA"      | false
        "set什么"      | true
        "setClass"   | true
        "setclass"   | false
        "setCLASS"   | true
    }

    def "test isSetter"() {
        expect:
        expect_result == BeanCons.isSetter(suppose_method)
        where:
        suppose_method                                                    | expect_result
        null                                                              | false
        Object.getMethod("hashCode")                                      | false
        Object.getMethod("getClass")                                      | false
        ClassLoader.getMethod("setClassAssertionStatus", String, boolean) | false//双参
        ClassLoader.getMethod("setDefaultAssertionStatus", boolean)       | true
        System.getMethod("setProperties", Properties)                     | false//静态方法
    }
}
