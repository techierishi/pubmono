import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: any;

  allTags: any;

  constructor(private cartService: CartService) { }

  ngOnInit() {

    this.cartService.getAll({
      onData : (response)=>{
        this.products = response;

        const _allTags = this.products.reduce((all,item,index)=>{
          item.tags.forEach(element => {
            all.push(element);
          });
          return all;
        },[]);

      
      this.allTags = new Set(_allTags);
      }
    });

  }

  addToCart(_product){
    this.cartService.updateProduct({
      productId: _product._id,
      onData : (response)=>{
        alert('Added to Cart')
      } 
    })
  }

  getFilteredProducts(_filterStr){
    this.cartService.getAllFilter({
      filterStr:_filterStr,
      onData : (response)=>{
        this.products = response;

       
      }
    });
  }
}
