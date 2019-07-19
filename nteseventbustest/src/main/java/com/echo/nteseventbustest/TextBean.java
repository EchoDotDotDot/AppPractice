package com.echo.nteseventbustest;

public class TextBean {
    private String A;
    private String B;

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    @Override
    public String toString() {
        return "TextBean{" +
                "A='" + A + '\'' +
                ", B='" + B + '\'' +
                '}';
    }

    public TextBean(String a, String b) {
        A = a;
        B = b;
    }
}
