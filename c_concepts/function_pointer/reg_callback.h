/* reg_callback.h */
typedef void (*callbackfunc)(void);
void on_error_occurred(callbackfunc ptr_callback);