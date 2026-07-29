#include <stdio.h>
#include <stdarg.h>
#include "main.h"
#include "db.h"


void getEmpValAndInsert();


int main(int argc, char* argv[]){

	int opt;
	
	printf("1 : Insert student \n");
	printf("2 : Show student \n");

	printf("Enter you choice :");
	scanf("%d",&opt);

	createDb();

	printf(" Your choice is %d \n", opt);

	switch(opt){
		case 1:
		getEmpValAndInsert();
		break;
		case 2:

		break;
	}
	return 0;
}

void getEmpValAndInsert(){

	emp_model em;
	printf(" Enter name :");
	scanf("%s",em.ename);

	printf(" Enter age :");
	scanf("%d",&em.eage);

	printf(" Enter address :");
	scanf("%s",em.addrss);

	printf(" Enter salary :");
	scanf("%g",&em.salary);


	insertEmp(&em);
}




