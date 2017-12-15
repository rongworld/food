package com.product.food.utils;

import java.io.File;

public class Test {
    public static void main(String [] args){
        File file = new File("a");
        System.out.print(file.getAbsolutePath());
    }
}
