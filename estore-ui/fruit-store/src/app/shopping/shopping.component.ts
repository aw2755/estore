import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { User } from '../user';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-shopping',
  templateUrl: './shopping.component.html',
  styleUrls: ['./shopping.component.css']
})
export class ShoppingComponent implements OnInit {

  products: Product[] = [];
  currentUser!: User;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage['currentUser']);
    this.currentUser.shoppingCat = [];
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products.slice(0, products.length));
  }

}