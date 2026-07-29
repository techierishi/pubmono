/* reg_callback.c */
#include<stdio.h>
#include"reg_callback.h"

void on_error_occurred(callbackfunc ptr_callback){

    printf(" Inside on_error_occurred function \n");

    //the passed function is called here
    (*ptr_callback)();
}