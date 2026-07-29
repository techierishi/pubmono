toStringForJava
===============

I used to override toString functino in my model class and I always have to make a string of all the variables . This javascript code will automatically do that for me :)

Try it on jsFiddle

http://jsfiddle.net/rishi_karmicksol/4uyhkz02/

This javascript will convert following variable declaration >>>

                               
 String expence_type;          
 String expense_category_val;  
 String expense_amount_val;    
 String expense_date_val;      
 String expense_note_val;      
 String attach_photo_name;     
 
 To following string which can easily placed insite an empty string in java to string function return value >>>>
 
[{\" expence_type\" : \""+ expence_type+"\"},{\" expense_category_val\" : \""+ expense_category_val+"\"},{\" expense_amount_val\" : \""+ expense_amount_val+"\"},{\" expense_date_val\" : \""+ expense_date_val+"\"},{\" expense_note_val\" : \""+ expense_note_val+"\"},{\" attach_photo_name\" : \""+ attach_photo_name+"\"}]

Example ::

 String str = "[{\" expence_type\" : \""+ expence_type+"\"},{\" expense_category_val\" : \""+ expense_category_val+"\"},{\" expense_amount_val\" : \""+ expense_amount_val+"\"},{\" expense_date_val\" : \""+ expense_date_val+"\"},{\" expense_note_val\" : \""+ expense_note_val+"\"},{\" attach_photo_name\" : \""+ attach_photo_name+"\"}]";
