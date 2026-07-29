typedef struct emp_model {
	int eid;
	char *ename;
	int eage;
	char *addrss;
	float salary;
	
}emp_model; 

int createDb();
int insertEmp(emp_model *emp);
int showEmp();