import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { User } from '../user';
import { UserService } from '../user.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  currentUser!: User;

  constructor(private productService: ProductService, private userService : UserService) { }

  ngOnInit(): void {
    this.getProducts();
    this.currentUser = JSON.parse(sessionStorage['currentUser']);
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products.slice(0, products.length));
  }

  add(name : string): void {
    this.userService.addProduct(this.currentUser.username, name).subscribe();
    alert("added " + name + " to cart");
  }

}