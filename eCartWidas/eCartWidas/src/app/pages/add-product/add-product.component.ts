import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  productModel = {
    price: "",
    product_name: "",
    purchased: false,
    tags: []
  }

  tagsStr: string;

  constructor(private cartService: CartService) { }

  ngOnInit() {
  }

  saveProduct(){
    this.productModel.tags = this.tagsStr.split(',');

    this.cartService.addProduct({
      productData : this.productModel,
      onData: (response)=>{
        alert('Product saved');
      }
    });

  }
}
