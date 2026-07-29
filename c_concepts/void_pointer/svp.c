#include<stdio.h>
#define CAST(dtype) (*(dt*) dtype)
typedef struct keyval{
	int key;
	void *val;
}kv;

typedef struct data{
	int id;
	char *name;
}dt;

int main(){
	kv k;
	dt dd;

	dd.id = 5;
	dd.name = "Rishi";

	k.key = 1;
	k.val = &dd;
	
	printf(" Key %d \n",k.key);
	/*
		In the next line k.val is void pointer which needs to be typecasted into dt structure first before getting 
		any value from it
	*/
	printf(" Value id : %d \n ", CAST(k.val).id);
	printf(" Value name : %s \n  ", CAST(k.val).name);
	return 0;
}
