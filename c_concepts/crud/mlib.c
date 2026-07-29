#include <stdio.h>
#include <stdarg.h>
#include "mlib.h"

char* varconcat( char* fmt, ...)
{
    char buf[500];     
    va_list vl;
    va_start(vl, fmt);

    vsnprintf( buf, sizeof( buf), fmt, vl);

    va_end( vl);

  return buf;
}