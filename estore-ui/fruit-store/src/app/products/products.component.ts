import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(
      products => this.products = products);
  }

  add(name: string, price: string, quantity:string): void {
    name = name.trim();
    let prices = parseFloat(price);
    if(prices < 0)
    {
      prices = 1;
    }
    
    let quantities = parseInt(quantity);
    if(quantities <= 0)
    {
      quantities = 1;
    }

    const product : Product = {
      name: name,
      price: prices,
      quantity: quantities
    }

    if (!name) { return; }
    this.productService.addProduct(product)
      .subscribe(product => {
        this.products.push(product)
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(h => h !== product);
    this.productService.deleteProduct(product.name).subscribe();
  }

}