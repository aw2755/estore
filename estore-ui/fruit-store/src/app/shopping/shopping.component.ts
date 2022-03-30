import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-shopping',
  templateUrl: './shopping.component.html',
  styleUrls: ['./shopping.component.css']
})
export class ShoppingComponent implements OnInit {

  cart: Product[] = [];
  currentUser!: User;

  constructor(private userService: UserService, private router : Router) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage['currentUser']);
    this.currentUser.shoppingCat = [];
    this.getItems();
  }

  getItems(): void {
    this.userService.getAll(this.currentUser.username).subscribe(
      cart => this.cart = cart
    );
  }

  remove(name : String): void {
    this.userService.removeProduct(this.currentUser.username, name).subscribe();
  }

  checkout(): void {
    this.userService.checkoutProducts(this.currentUser.username).subscribe();
    this.router.navigateByUrl("/dashboard");
  }

}