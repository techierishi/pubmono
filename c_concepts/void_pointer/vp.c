#include<stdio.h>

int main(){

	int i;
	char c;
	void *the_data;

	i=6;
	c='a';

	the_data = &i;

	printf("The data at int : %d \n",*(int*)the_data);
}
