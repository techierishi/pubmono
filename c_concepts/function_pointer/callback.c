/* callback.c */
#include<stdio.h>
#include"reg_callback.h"

void error_occurred(void){
    printf(" Error occurred in the program .. \n");
}

int main(void){

    /* initialize function pointer to
        error_occurred

        callbackfunc is the type of function pointer declared in reg_callback.h
     */
    callbackfunc func_ptr = error_occurred;

    printf("This is a program demonstrating function callback\n");

    /* We just wrote the body of error_occurred() but it will be called from inside the on_error_occurred() function

        as we are passing the address of error_occurred() to on_error_occurred()
     */
    on_error_occurred(func_ptr);

    return 0;
}

