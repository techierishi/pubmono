import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  public static BASE_API = "http://127.0.0.1:5000/";

  constructor(private http: HttpClient){
  }

  getAll(params) {
    this.http.get(CartService.BASE_API+'all').subscribe(data => {
      console.log(data);
      if(params.onData){
        params.onData(data);
      }
    });
  }

  getAllFilter(params) {
    this.http.get(CartService.BASE_API+'all?filter='+params.filterStr).subscribe(data => {
      console.log(data);
      if(params.onData){
        params.onData(data);
      }
    });
  }

  addProduct(params) {
    this.http.post(CartService.BASE_API+'add', params.productData).subscribe(data => {
      console.log(data);
      if(params.onData){
        params.onData(data);
      }
    });
  }
  updateProduct(params) {
    this.http.get(CartService.BASE_API+'update/'+params.productId).subscribe(data => {
      console.log(data);
      if(params.onData){
        params.onData(data);
      }
    });
  }

}
